package museum.service.services.implementation;

import lombok.extern.slf4j.Slf4j;
import museum.service.models.DTOs.BankNotificationDTO;
import museum.service.models.DTOs.TourDTO;
import museum.service.models.DTOs.TourPurchaseDTO;
import museum.service.models.entities.TourpurchaseEntity;
import museum.service.models.entities.UserEntity;
import museum.service.services.BankNotificationService;
import museum.service.services.EmailSender;
import museum.service.repositories.TourTicketsRepository;
import museum.service.services.TourBeginningNotificationService;
import museum.service.utilities.TourPDFCreator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class BankNotificationServiceImpl implements BankNotificationService
{
    private final ModelMapper modelMapper;
    private final TourTicketsRepository tourTicketsRepository;
    private final EmailSender emailSender;
    private final TourBeginningNotificationService tourBeginningNotificationService;

    @Value("${ticket.email_notification.title}")
    private String emailTitle;

    @Value("${ticket.email_notification.body}")
    private String emailMessage;

    public BankNotificationServiceImpl(ModelMapper modelMapper, TourTicketsRepository tourTicketsRepository, EmailSender emailSender, TourBeginningNotificationService tourBeginningNotificationService)
    {
        this.modelMapper = modelMapper;
        this.tourTicketsRepository = tourTicketsRepository;
        this.emailSender = emailSender;
        this.tourBeginningNotificationService = tourBeginningNotificationService;
    }

    @Override
    public void handlePayment(BankNotificationDTO notificationDTO)
    {
        UUID ticketID = UUID.fromString(notificationDTO.getScratchString());

        Optional<TourpurchaseEntity> tourPurchaseEntity = tourTicketsRepository.findById(ticketID);

        if(tourPurchaseEntity.isEmpty())
        {
            return;
        }

        TourpurchaseEntity tourPurchase = tourPurchaseEntity.get();

        if(notificationDTO.getStatus().equals(BankNotificationDTO.PaymentStatus.UNSUCCESSFUL))
        {
            tourTicketsRepository.delete(tourPurchase);
            return;
        }

        tourPurchase.setPaid(LocalDateTime.now());
        tourPurchase = tourTicketsRepository.saveAndFlush(tourPurchase);

        TourDTO tourDTO = modelMapper.map(tourPurchase.getTour(), TourDTO.class);
        tourDTO.setPurchased(tourPurchase.getPurchased());
        tourDTO.setPaid(tourPurchase.getPaid());

        TourPurchaseDTO tourPurchaseDTO = modelMapper.map(tourPurchase, TourPurchaseDTO.class);

        UserEntity buyer = tourPurchase.getUser();

        // email the customer
        try
        {
            InputStreamSource pdfStream = TourPDFCreator.createPDFTicket(tourDTO, tourPurchaseDTO);
            emailSender.sendEmail(buyer.getEmail(), emailTitle, emailMessage, pdfStream);
        }
        catch(Exception e)
        {
            // log the exception
            log.warn("Bank notification service has thrown an exception: ", e);
        }
    }
}
