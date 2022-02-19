package museum.service.controllers;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.MuseumDTO;
import museum.service.models.DTOs.MuseumTypeDTO;
import museum.service.models.DTOs.TourDTO;
import museum.service.models.DTOs.WeatherDTO;
import museum.service.models.entities.MuseumTypeEntity;
import museum.service.services.MuseumService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/museum")
public class MuseumController
{
    private final MuseumService museumService;

    public MuseumController(MuseumService museumService)
    {
        this.museumService = museumService;
    }

    @GetMapping("{museumID}/tour")
    public List<TourDTO> getMuseumTours(@PathVariable Integer museumID)
    {
        return museumService.getTours(museumID);
    }

    @GetMapping("/type")
    public List<MuseumTypeDTO> getMuseumTypes()
    {
        return museumService.getMuseumTypes();
    }

    @PostMapping("/{museumID}/tour/{tourID}/tickets")
    public String buyTicket(@PathVariable Integer museumID, @PathVariable Integer tourID, Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());

        return museumService.buyTicket(tourID, userDetails.getId());
    }

    @GetMapping
    public List<MuseumDTO> getMuseums(@RequestParam Map<String, String> params)
    {
        return museumService.getMuseums(params);
    }

    @GetMapping("/{id}")
    public MuseumDTO getMuseum(@PathVariable(name = "id") Integer museumID)
    {
        return museumService.getMuseum(museumID);
    }

    @GetMapping("/{museumID}/weather")
    public List<WeatherDTO> getWeathers(@PathVariable Integer museumID)
    {
        return museumService.getWeather(museumID);
    }
}
