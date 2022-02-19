package museum.service.services;


import museum.service.models.DTOs.MuseumDTO;
import museum.service.models.DTOs.MuseumTypeDTO;
import museum.service.models.DTOs.TourDTO;

import java.util.List;
import java.util.Map;

public interface MuseumService
{
    String buyTicket(Integer tourID, Integer buyerID);
    List<MuseumDTO> getMuseums(Map<String, String> params);
    MuseumDTO getMuseum(Integer id);
    List<TourDTO> getTours(Integer museumID);

    MuseumTypeDTO addMuseumType(MuseumTypeDTO typeDTO);
    List<MuseumTypeDTO> getMuseumTypes();
}
