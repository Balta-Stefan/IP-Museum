package museum.service.controllers;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.MuseumDTO;
import museum.service.services.MuseumService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/museum")
public class MuseumController
{
    private final MuseumService museumService;

    public MuseumController(MuseumService museumService)
    {
        this.museumService = museumService;
    }

    @PostMapping("/{museumID}/tour/{tourID}/tickets")
    public String buyTicket(@PathVariable Integer museumID, @PathVariable Integer tourID, Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());

        return museumService.buyTicket(tourID, userDetails.getId());
    }

    @GetMapping
    public List<MuseumDTO> getMuseums()
    {
        return museumService.getMuseums();
    }

    @GetMapping("/{id}")
    public MuseumDTO getMuseum(@PathVariable(name = "id") Integer museumID)
    {
        return museumService.getMuseum(museumID);
    }
}
