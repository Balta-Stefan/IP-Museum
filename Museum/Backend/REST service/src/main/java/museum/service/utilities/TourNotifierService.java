package museum.service.utilities;

import lombok.extern.slf4j.Slf4j;
import museum.service.models.DTOs.TourDTO;
import museum.service.models.entities.TourEntity;
import museum.service.models.entities.TourpurchaseEntity;
import museum.service.models.entities.UserEntity;
import museum.service.repositories.ToursRepository;
import museum.service.services.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class TourNotifierService
{
    private final ToursRepository toursRepository;
    private final EmailSender emailSender;

    public TourNotifierService(ToursRepository toursRepository, EmailSender emailSender)
    {
        this.toursRepository = toursRepository;
        this.emailSender = emailSender;
    }


    @Async
    @Transactional
    public void notifyUser(Integer tourID)
    {
        Optional<TourEntity> tourEntityOpt = toursRepository.findById(tourID);
        if(tourEntityOpt.isEmpty())
        {
            return;
        }
        TourEntity tourEntity = tourEntityOpt.get();


        for(TourpurchaseEntity purchase : tourEntity.getPurchases())
        {
            UserEntity userEntity = purchase.getUser();


            String receiver = userEntity.getEmail();
            String title = "Obavjestenje o pocetku obilaska.";
            String message = "Obilazak pocinje u: " + tourEntity.getStartTimestamp().toLocalTime().toString();

            try
            {
                emailSender.sendEmail(receiver, title, message, null);
            }
            catch (Exception e)
            {
                log.warn("Notification service has thrown an exception: ", e);
            }
        }
    }
}
