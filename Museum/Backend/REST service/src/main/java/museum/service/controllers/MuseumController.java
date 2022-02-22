package museum.service.controllers;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.*;
import museum.service.services.MuseumService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public List<TourDTO> getMuseumTours(@PathVariable Integer museumID, Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());

        return museumService.getTours(museumID, userDetails.getId());
    }

    @GetMapping("{museumID}/tour/{tourID}")
    public TourDTO getTour(@PathVariable Integer museumID, @PathVariable Integer tourID, Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());
        return museumService.getTour(museumID, tourID, userDetails.getId());
    }

    @GetMapping("/type")
    public List<MuseumTypeDTO> getMuseumTypes()
    {
        return museumService.getMuseumTypes();
    }

    @PostMapping("/{museumID}/tour/{tourID}/tickets")
    public TicketPurchaseResponse buyTicket(@PathVariable Integer museumID, @PathVariable Integer tourID, Authentication authentication)
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
