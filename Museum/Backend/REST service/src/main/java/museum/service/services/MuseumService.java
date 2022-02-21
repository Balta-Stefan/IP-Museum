package museum.service.services;


import museum.service.models.DTOs.*;

import java.util.List;
import java.util.Map;

public interface MuseumService
{
    String buyTicket(Integer tourID, Integer buyerID);
    List<MuseumDTO> getMuseums(Map<String, String> params);
    MuseumDTO getMuseum(Integer id);
    List<TourDTO> getTours(Integer museumID);
    TourDTO getTour(Integer museumID, Integer tourID);

    MuseumTypeDTO addMuseumType(MuseumTypeDTO typeDTO);
    List<MuseumTypeDTO> getMuseumTypes();

    List<WeatherDTO> getWeather(Integer museumID);
    void addStaticContent(Integer tourID, List<TourStaticContentDTO> staticContentDTOS);
}
