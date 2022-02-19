package museum.service.services.implementation;

import museum.service.exceptions.NotFoundException;
import museum.service.models.DTOs.MuseumDTO;
import museum.service.models.DTOs.TourDTO;
import museum.service.models.entities.MuseumEntity;
import museum.service.models.entities.TourEntity;
import museum.service.models.entities.TourpurchaseEntity;
import museum.service.models.entities.UserEntity;
import museum.service.models.requests.PaymentRequest;
import museum.service.models.responses.PaymentRequestResponse;
import museum.service.services.MuseumService;
import museum.service.repositories.MuseumsRepository;
import museum.service.repositories.TourTicketsRepository;
import museum.service.repositories.ToursRepository;
import museum.service.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MuseumServiceImpl implements MuseumService
{
    private final ToursRepository toursRepository;
    private final UserRepository userRepository;
    private final MuseumsRepository museumsRepository;
    private final TourTicketsRepository tourTicketsRepository;

    private final ModelMapper modelMapper;

    @Value("${transactions.notify_url}")
    private String transactionNotificationURL;

    @Value("${bank_account.username}")
    private String bankAccountUsername;

    @Value("${bank_account.password}")
    private String bankAccountPassword;

    @Value("${bank_url}")
    private String bank_url;

    public MuseumServiceImpl(ToursRepository toursRepository, UserRepository userRepository, MuseumsRepository museumsRepository, TourTicketsRepository tourTicketsRepository, ModelMapper modelMapper)
    {
        this.toursRepository = toursRepository;
        this.userRepository = userRepository;
        this.museumsRepository = museumsRepository;
        this.tourTicketsRepository = tourTicketsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String buyTicket(Integer tourID, Integer buyerID)
    {
        TourEntity tourEntity = toursRepository.findById(tourID).orElseThrow(NotFoundException::new);
        UserEntity user = userRepository.findById(buyerID).orElseThrow(NotFoundException::new);

        TourpurchaseEntity tourpurchaseEntity = new TourpurchaseEntity();
        tourpurchaseEntity.setPurchaseId(null);
        tourpurchaseEntity.setTour(tourEntity);
        tourpurchaseEntity.setPurchased(LocalDateTime.now());
        tourpurchaseEntity.setUser(user);
        tourpurchaseEntity = tourTicketsRepository.saveAndFlush(tourpurchaseEntity);

        PaymentRequest request = new PaymentRequest(tourEntity.getPrice(), transactionNotificationURL, tourpurchaseEntity.getPurchaseId().toString());

        WebClient client = WebClient.builder()
                .defaultHeaders(header -> header.setBasicAuth(bankAccountUsername, bankAccountPassword))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(bank_url)
                .build();

        PaymentRequestResponse paymentResponse = client.post()
                .body(Mono.just(request), PaymentRequest.class)
                .retrieve()
                .bodyToMono(PaymentRequestResponse.class)
                .timeout(Duration.ofSeconds(6))
                .block();

        return paymentResponse.getRedirectURL();
    }

    @Override
    public List<MuseumDTO> getMuseums(Map<String, String> params)
    {
        String name = params.get("name");
		String country = params.get("country");
        String city = params.get("city");

        if(name != null && name.equals("null"))
        {
            name = null;
        }
        if(city != null && city.equals("null"))
        {
            city = null;
        }
		 if(country != null && country.equals("null"))
        {
            country = null;
        }
		

        List<MuseumEntity> museums = museumsRepository.filterMuseums(name, country, city);

        return museums
                .stream()
                .map(m -> modelMapper.map(m, MuseumDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MuseumDTO getMuseum(Integer id)
    {
        MuseumEntity museum = museumsRepository.findById(id).orElseThrow(NotFoundException::new);

        return modelMapper.map(museum, MuseumDTO.class);
    }

    @Override
    public List<TourDTO> getTours(Integer museumID)
    {
        MuseumEntity museum = museumsRepository.findById(museumID).orElseThrow(NotFoundException::new);

        List<TourEntity> tours = toursRepository.findAllByMuseum(museum);

        return tours
                .stream()
                .map(t -> modelMapper.map(t, TourDTO.class))
                .collect(Collectors.toList());
    }
}
