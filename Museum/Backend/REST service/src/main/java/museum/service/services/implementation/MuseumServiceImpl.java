package museum.service.services.implementation;

import museum.service.exceptions.ConflictException;
import museum.service.exceptions.NotFoundException;
import museum.service.models.DTOs.*;
import museum.service.models.entities.*;
import museum.service.models.enums.StaticResourceType;
import museum.service.models.requests.PaymentRequest;
import museum.service.models.responses.PaymentRequestResponse;
import museum.service.repositories.*;
import museum.service.services.*;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MuseumServiceImpl implements MuseumService
{
    private final ToursRepository toursRepository;
    private final UserRepository userRepository;
    private final MuseumsRepository museumsRepository;
    private final TourTicketsRepository tourTicketsRepository;
    private final MuseumTypeRepository museumTypeRepository;
    private final TourStaticContentRepository tourStaticContentRepository;
    private final CountriesService countriesService;
    private final WeatherService weatherService;
    private final FileService fileService;
    private final TourBeginningNotificationService tourTaskScheduler;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private final String transactionNotificationURL;

    private final String bankAccountUsername;

    private final String bankAccountPassword;


    private final String bank_url;

    private final long monoTimeoutSeconds = 6;

    private final WebClient bankWebClient;


    public MuseumServiceImpl(ToursRepository toursRepository,
                             UserRepository userRepository,
                             MuseumsRepository museumsRepository,
                             TourTicketsRepository tourTicketsRepository,
                             MuseumTypeRepository museumTypeRepository,
                             TourStaticContentRepository tourStaticContentRepository,
                             CountriesService countriesService,
                             WeatherService weatherService,
                             FileService fileService,
                             TourBeginningNotificationService tourTaskScheduler,
                             ModelMapper modelMapper,
                             @Value("${transactions.notify_url}") String transactionNotificationURL,
                             @Value("${bank_account.username}") String bankAccountUsername,
                             @Value("${bank_account.password}") String bankAccountPassword,
                             @Value("${bank_url}") String bank_url)
    {
        this.toursRepository = toursRepository;
        this.userRepository = userRepository;
        this.museumsRepository = museumsRepository;
        this.tourTicketsRepository = tourTicketsRepository;
        this.museumTypeRepository = museumTypeRepository;
        this.tourStaticContentRepository = tourStaticContentRepository;
        this.countriesService = countriesService;
        this.weatherService = weatherService;
        this.fileService = fileService;
        this.tourTaskScheduler = tourTaskScheduler;
        this.modelMapper = modelMapper;
        this.transactionNotificationURL = transactionNotificationURL;
        this.bankAccountUsername = bankAccountUsername;
        this.bankAccountPassword = bankAccountPassword;
        this.bank_url = bank_url;

        this.bankWebClient = WebClient.builder()
            .defaultHeaders(header -> header.setBasicAuth(bankAccountUsername, bankAccountPassword))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl(bank_url)
            .build();
    }

    @Override
    public TicketPurchaseResponse buyTicket(Integer tourID, Integer buyerID)
    {
        TourEntity tourEntity = toursRepository.findById(tourID).orElseThrow(NotFoundException::new);
        UserEntity user = userRepository.findById(buyerID).orElseThrow(NotFoundException::new);

        // one user can have only one ticket
        Optional<TourpurchaseEntity> purchaseEntity = tourTicketsRepository.findTicket(tourID, buyerID);
        if(purchaseEntity.isPresent())
        {
            TourpurchaseEntity tempEnt = purchaseEntity.get();
            // if the ticket has already been paid, return 409
            if(tempEnt.getPaid() != null)
            {
                throw new ConflictException();
            }
            return new TicketPurchaseResponse(tempEnt.getPaymentURL(), tempEnt.getPurchaseId().toString());
        }

        TourpurchaseEntity tourpurchaseEntity = new TourpurchaseEntity();
        tourpurchaseEntity.setTour(tourEntity);
        tourpurchaseEntity.setPurchased(LocalDateTime.now());
        tourpurchaseEntity.setUser(user);
        tourpurchaseEntity = tourTicketsRepository.saveAndFlush(tourpurchaseEntity);

        PaymentRequest request = new PaymentRequest(tourEntity.getPrice(), transactionNotificationURL, tourpurchaseEntity.getPurchaseId().toString());



        PaymentRequestResponse paymentResponse = bankWebClient.post()
                .body(Mono.just(request), PaymentRequest.class)
                .retrieve()
                .bodyToMono(PaymentRequestResponse.class)
                .timeout(Duration.ofSeconds(6))
                .block();

        tourpurchaseEntity.setPaymentURL(paymentResponse.getRedirectURL());
        tourpurchaseEntity = tourTicketsRepository.saveAndFlush(tourpurchaseEntity);

        return new TicketPurchaseResponse(paymentResponse.getRedirectURL(), tourpurchaseEntity.getPurchaseId().toString());
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
    public List<TourDTO> getTours(Integer museumID, Integer userID)
    {
        MuseumEntity museum = museumsRepository.findById(museumID).orElseThrow(NotFoundException::new);

        List<TourEntity> tours = toursRepository.findAllUnfinishedTours(museumID, LocalDateTime.now());

        List<TourDTO> tourDTOs = tours
                .stream()
                .map(t -> modelMapper.map(t, TourDTO.class))
                .collect(Collectors.toList());

        // determine for which tours the user owns a ticket
        tourDTOs.forEach(t ->
        {
            Optional<TourpurchaseEntity> tourpurchase = tourTicketsRepository.findTicket(t.getTourID(), userID);

            if(tourpurchase.isPresent())
            {
                TourpurchaseEntity purchase = tourpurchase.get();
                t.setPurchased(purchase.getPurchased());
                t.setPaid(purchase.getPaid());
            }
        });

        tours.forEach(t -> t.setStaticContent(null));

        return tourDTOs;
    }

    @Override
    public TourDTO getTour(Integer museumID, Integer tourID, Integer requesterID)
    {
        // determine whether the user can access the tour
        Optional<TourpurchaseEntity> tourPurchase = tourTicketsRepository.findTicket(tourID, requesterID);

        TourEntity tour = toursRepository.findById(tourID).orElseThrow(NotFoundException::new);


        TourDTO tempDTO = modelMapper.map(tour, TourDTO.class);

        // if the tour hasn't started or the user hasn't purchased the ticket, don't give static content
        if(tourPurchase.isPresent())
        {
            TourpurchaseEntity purchase = tourPurchase.get();

            tempDTO.setPurchased(purchase.getPurchased());
            tempDTO.setPaid(purchase.getPaid());

            LocalDateTime currentTime = LocalDateTime.now();
            if(currentTime.isBefore(tour.getStartTimestamp()) || currentTime.isAfter(tour.getEndTimeStamp()))
            {
                tempDTO.setStaticContent(null);
            }
        }
        else
        {
            tempDTO.setStaticContent(null);
        }

        return tempDTO;
    }

    @Override
    public MuseumTypeDTO addMuseumType(@Valid MuseumTypeDTO typeDTO)
    {
        MuseumTypeEntity museumTypeEntity = modelMapper.map(typeDTO, MuseumTypeEntity.class);
        museumTypeEntity.setMuseumTypeID(null);

        typeDTO.setType(typeDTO.getType().trim());
        if(museumTypeRepository.findByTypeIgnoreCase(typeDTO.getType()).isPresent())
        {
            throw new ConflictException();
        }

        museumTypeEntity = museumTypeRepository.saveAndFlush(museumTypeEntity);

        return modelMapper.map(museumTypeEntity, MuseumTypeDTO.class);
    }

    @Override
    public List<MuseumTypeDTO> getMuseumTypes()
    {
        return museumTypeRepository
                .findAll()
                .stream()
                .map(t -> modelMapper.map(t, MuseumTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WeatherDTO> getWeather(Integer museumID)
    {
        MuseumEntity museum = museumsRepository.findById(museumID).orElseThrow(NotFoundException::new);
        Optional<RegionDTO[]> regionsOptional = countriesService
                .getRegions(museum.getCountryAlpha2Code())
                .blockOptional(Duration.ofSeconds(monoTimeoutSeconds));

        if(regionsOptional.isEmpty() || regionsOptional.get().length == 0)
        {
            return Collections.emptyList();
        }
        RegionDTO[] regions = regionsOptional.get();

        Random rnd = new Random();
        RegionDTO randomRegion = regions[rnd.nextInt(regions.length)];

        Optional<CityDTO[]> citiesOptional = countriesService
                .getCities(museum.getCountryAlpha2Code(), randomRegion.getRegion())
                .blockOptional(Duration.ofSeconds(monoTimeoutSeconds));

        if(citiesOptional.isEmpty() || citiesOptional.get().length == 0)
        {
            return Collections.emptyList();
        }
        List<CityDTO> cities = Arrays.asList(citiesOptional.get());

        List<CityDTO> randomCities = new ArrayList<>();

        final int numOfCitiesToSelect = 3;

        if(cities.size() <= numOfCitiesToSelect)
        {
            randomCities = cities;
        }
        else
        {
            while(randomCities.size() < numOfCitiesToSelect)
            {
                CityDTO tempCity = cities.get(rnd.nextInt(cities.size()));
                if(randomCities.contains(tempCity))
                {
                    continue;
                }

                randomCities.add(tempCity);
            }
        }

        List<WeatherDTO> weathers = new ArrayList<>(numOfCitiesToSelect);
        randomCities.forEach(c ->
        {
            weathers.add(weatherService.getWeather(c.getLatitude(), c.getLongitude()));
        });


        return weathers;
    }

    @Override
    public void addStaticContent(Integer tourID, List<TourStaticContentDTO> staticContentDTOS)
    {
        TourEntity tourEntity = toursRepository.findById(tourID).orElseThrow(NotFoundException::new);
        List<TourStaticContent> staticContent = staticContentDTOS.stream()
                .map(s -> modelMapper.map(s, TourStaticContent.class))
                .collect(Collectors.toList());
        staticContent.forEach(s -> s.setStaticContentID(null));

        tourEntity.getStaticContent().addAll(staticContent);
        toursRepository.saveAndFlush(tourEntity);
    }

    @Override
    public MuseumDTO createMuseum(MuseumDTO museum)
    {
        MuseumEntity museumEntity = modelMapper.map(museum, MuseumEntity.class);
        museumEntity.setMuseumId(null);

        museumEntity = museumsRepository.saveAndFlush(museumEntity);
        entityManager.refresh(museumEntity);

        return modelMapper.map(museumEntity, MuseumDTO.class);
    }

    @Override
    public TourDTO addTour(Integer museumID, FormTourDTO tourDTO) throws IOException
    {
        MuseumEntity museumEntity = museumsRepository.findById(museumID).orElseThrow(NotFoundException::new);

        TourEntity tourEntity = new TourEntity();

        LocalDateTime startDateTime = LocalDateTime.of(tourDTO.getStartDate(), tourDTO.getStartTime());

        tourEntity.setStartTimestamp(startDateTime);
        tourEntity.setEndTimeStamp(startDateTime.plusHours(tourDTO.getDurationHours()));
        tourEntity.setPrice(tourDTO.getPrice());
        tourEntity.setMuseum(museumEntity);

        tourEntity = toursRepository.saveAndFlush(tourEntity);
        entityManager.refresh(tourEntity);

        List<TourStaticContentDTO> contentDTOS = new ArrayList<>();
        // save static files.File name will be formed as: tourID + UUID
        String youtubeLink = tourDTO.getYoutubeLink();
        if(youtubeLink != null && youtubeLink.trim().equals(Strings.EMPTY) == false)
        {
            String embedYtLink = "https://youtube.com/embed/" + youtubeLink.substring(youtubeLink.indexOf("=") + 1);

            TourStaticContentDTO tmp = new TourStaticContentDTO();
            tmp.setURI(embedYtLink);
            tmp.setIsYouTubeVideo(true);
            tmp.setResourceType(StaticResourceType.VIDEO);

            contentDTOS.add(tmp);
        }
        for(MultipartFile f : tourDTO.getPictures())
        {
            TourStaticContentDTO tmp = new TourStaticContentDTO();
            tmp.setIsYouTubeVideo(false);
            tmp.setResourceType(StaticResourceType.PICTURE);

            String fileName = tourEntity.getTourId() + UUID.randomUUID().toString() + "." + f.getContentType().split("/")[1];
            tmp.setURI(fileName);
            this.fileService.save(f, fileName);

            contentDTOS.add(tmp);
        }
        MultipartFile video = tourDTO.getVideo();
        if(video != null && video.isEmpty() == false)
        {
            TourStaticContentDTO tmp = new TourStaticContentDTO();
            tmp.setIsYouTubeVideo(false);
            tmp.setResourceType(StaticResourceType.VIDEO);

            // set URI
            String videoExtension = video.getContentType().split("/")[1];
            if(videoExtension.equals("x-matroska"))
            {
                videoExtension = "mkv";
            }
            String fileName = tourEntity.getTourId() + UUID.randomUUID().toString() + "." + videoExtension;
            tmp.setURI(fileName);

            this.fileService.save(video, fileName);

            contentDTOS.add(tmp);
        }

        for(TourStaticContentDTO t : contentDTOS)
        {
            TourStaticContent tmp = modelMapper.map(t, TourStaticContent.class);
            tmp.setStaticContentID(null);
            tmp.setTour(tourEntity);

            tourStaticContentRepository.saveAndFlush(tmp);
        }

        entityManager.refresh(tourEntity);

        TourDTO tourDTO2 = modelMapper.map(tourEntity, TourDTO.class);
        this.tourTaskScheduler.addTour(tourDTO2);


        return tourDTO2;
    }
}
