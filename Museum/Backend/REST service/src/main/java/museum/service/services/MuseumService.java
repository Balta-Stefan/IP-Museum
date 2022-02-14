package museum.service.services;


import museum.service.models.DTOs.MuseumDTO;

import java.util.List;

public interface MuseumService
{
    String buyTicket(Integer tourID, Integer buyerID);
    List<MuseumDTO> getMuseums();
    MuseumDTO getMuseum(Integer id);
}
