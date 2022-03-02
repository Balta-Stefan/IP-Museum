package museum.service.controllers.mvc;

import lombok.extern.slf4j.Slf4j;
import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.*;
import museum.service.models.entities.MuseumEntity;
import museum.service.models.entities.MuseumTypeEntity;
import museum.service.models.entities.UserEntity;
import museum.service.repositories.MuseumTypeRepository;
import museum.service.services.AdminInfoService;
import museum.service.services.AdminLoginService;
import museum.service.services.CountriesService;
import museum.service.services.MuseumService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ADMIN')")
@Slf4j
public class AdminDashboardController
{
    private final AdminLoginService adminLoginService;
    private final AdminInfoService adminInfoService;
    private final CountriesService countriesService;
    private final MuseumService museumService;

    private final MuseumTypeRepository museumTypeRepository;

    private final ModelMapper modelMapper;

    private final long monoTimeoutSeconds = 6;

    public AdminDashboardController(AdminLoginService adminLoginService, AdminInfoService adminInfoService, CountriesService countriesService, MuseumService museumService, MuseumTypeRepository museumTypeRepository, ModelMapper modelMapper)
    {
        this.adminLoginService = adminLoginService;
        this.adminInfoService = adminInfoService;
        this.countriesService = countriesService;
        this.museumService = museumService;
        this.museumTypeRepository = museumTypeRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/countries/{country}/regions")
    @ResponseBody
    public RegionDTO[] getRegions(@PathVariable(name = "country") String countryAlpha2Code)
    {
        Mono<RegionDTO[]> regionsMono = this.countriesService.getRegions(countryAlpha2Code);

        return regionsMono.blockOptional(Duration.ofSeconds(monoTimeoutSeconds)).orElseGet(() -> new RegionDTO[0]);
    }

    @GetMapping("/countries/{country}/regions/{region}/cities")
    @ResponseBody
    public CityDTO[] getCities(@PathVariable(name = "country") String countryAlpha2Code,
                               @PathVariable(name = "region") String region)
    {
        Mono<CityDTO[]> citiesMono = this.countriesService.getCities(countryAlpha2Code, region);

        return citiesMono.blockOptional(Duration.ofSeconds(monoTimeoutSeconds)).orElseGet(() -> new CityDTO[0]);
    }


    @GetMapping
    public String getAdminPanel(Model model)
    {
        model.addAttribute("numOfLoggedInUsers", this.adminInfoService.getNumberOfLoggedInUsers());
        model.addAttribute("numOfRegisteredUsers", this.adminInfoService.getNumberOfRegisteredUsers());
        model.addAttribute("usersPerHours", this.adminInfoService.getLoginsPerHours());

        return "admin-dashboard";
    }

    @GetMapping("/login")
    public String login(@RequestParam("token") String token, HttpServletRequest request)
    {
        CustomUserDetails userDetails = adminLoginService.loginAdmin(token);

        request.getSession().setAttribute("admin", true);
        request.getSession().setAttribute("userDetails", userDetails);
        return "redirect:/admin";
    }



    @GetMapping("/users")
    public String getUsersPanel(@RequestParam(defaultValue = "false") Boolean onlyInactive,
                                @RequestParam(defaultValue = "0") Integer pageNumber,
                                @RequestParam(defaultValue = "30") Integer pageSize,
                                Model model)
    {
        Page<UserEntity> page = this.adminInfoService.getUsers(onlyInactive, pageNumber, pageSize);
        List<UserDTO> users = page
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList();


        model.addAttribute("users", users);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("onlyInactive", onlyInactive);
        model.addAttribute("totalUsers", page.getTotalElements());
        model.addAttribute("totalPages", Math.floor(page.getTotalPages()));

        return "users-panel";
    }


    @PostMapping("/users")
    public String changeUserInfo(@RequestParam(required = false) Boolean newStatus,
                                     @RequestParam(required = false) Boolean changePassword,
                                     @RequestParam(required = false) Integer userID,
                                     @RequestParam(defaultValue = "false") Boolean onlyInactive,
                                     @RequestParam(defaultValue = "0") Integer pageNumber,
                                     @RequestParam(defaultValue = "30") Integer pageSize,
                                     Model model)
    {
        String message = "";

        if(newStatus != null && userID != null)
        {
            boolean success = this.adminInfoService.changeUserActivityStatus(userID, newStatus);
            message = (success == true) ? "Izmjena statusa aktivnosti uspješna" : "Izmjena statusa aktivnosti neuspješna";
        }
        else if(changePassword != null && userID != null)
        {
            message = "Nova lozinka je: " + this.adminInfoService.changeUserPassword(userID);
        }

        model.addAttribute("message", message);
        Page<UserEntity> page = this.adminInfoService.getUsers(onlyInactive, pageNumber, pageSize);
        List<UserDTO> users = page
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList();

        model.addAttribute("users", users);
        model.addAttribute("onlyInactive", onlyInactive);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalUsers", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());


        return "users-panel";
    }

    @GetMapping("/museums")
    public String getMuseumsPanel(@RequestParam(defaultValue = "0") Integer pageNumber,
                                  @RequestParam(defaultValue = "30") Integer pageSize,
                                  Model model)
    {
        Page<MuseumEntity> museumsPage = this.adminInfoService.getMuseums(pageNumber, pageSize);
        List<MuseumDTO> museums = museumsPage
                .get()
                .map(m -> modelMapper.map(m, MuseumDTO.class))
                .toList();

        model.addAttribute("museums", museums);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalMuseums", museumsPage.getTotalElements());
        model.addAttribute("totalPages", museumsPage.getTotalPages());

        return "museums-panel";
    }

    @GetMapping("/museums/{museumID}/tours")
    public String getMuseumTours(@PathVariable Integer museumID, Model model)
    {
        MuseumDTO museumDTO = museumService.getMuseum(museumID);

        model.addAttribute("museum", museumDTO);

        return "museum-tours-panel";
    }

    @PostMapping("/museums/{museumID}/tours")
    public String addMuseumTour(@PathVariable Integer museumID, Model model,
                                @Valid @ModelAttribute FormTourDTO tourDTO) throws IOException
    {
        TourDTO tour = this.museumService.addTour(museumID, tourDTO);

        model.addAttribute("tourSubmitMessage", "Posjeta uspješno dodana.");
        return "museum-tours-panel";
    }


    @GetMapping("/museums/new")
    public String getCreateMuseumForm(Model model)
    {
        Mono<CountryDTO[]> countriesMono = this.countriesService.getCountries();

        List<MuseumTypeDTO> museumTypes = this.museumService.getMuseumTypes();

        model.addAttribute("museumTypes", museumTypes);

        try
        {
            Optional<CountryDTO[]> countriesOptional = countriesMono.blockOptional(Duration.ofSeconds(monoTimeoutSeconds));
            CountryDTO[] countries = countriesOptional.orElseGet(() -> new CountryDTO[0]);

            model.addAttribute("countries", countries);
        }
        catch(Exception e)
        {
            log.warn("Admin dashboard controller has thrown an exception: ", e);
        }

        return "add-new-museum";
    }

    @PostMapping("/museums/new")
    public String createNewMuseum(@Valid @ModelAttribute FormMuseumDTO museumDTO, Model model)
    {
        Optional<MuseumTypeEntity> museumTypeEntity = museumTypeRepository.findById(museumDTO.getType());
        if(museumTypeEntity.isEmpty())
        {
            model.addAttribute("formSubmissionMessage", "Zadani tip muzeja ne postoji");
            return "add-new-museum";
        }
        MuseumTypeDTO museumTypeDTO = modelMapper.map(museumTypeEntity.get(), MuseumTypeDTO.class);
        MuseumDTO museumDTO2 = modelMapper.map(museumDTO, MuseumDTO.class);
        museumDTO2.setType(museumTypeDTO);

        MuseumDTO createdMuseum = this.museumService.createMuseum(museumDTO2);

        model.addAttribute("formSubmissionMessage", "Dodavanje muzeja uspješno");

        return "add-new-museum";
    }


}
