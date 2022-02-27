package museum.service.services;


import museum.service.models.DTOs.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MuseumService
{
    TicketPurchaseResponse buyTicket(Integer tourID, Integer buyerID);
    List<MuseumDTO> getMuseums(Map<String, String> params);
    MuseumDTO getMuseum(Integer id);
    List<TourDTO> getTours(Integer museumID, Integer requesterID);
    TourDTO getTour(Integer museumID, Integer tourID, Integer requesterID);

    MuseumTypeDTO addMuseumType(MuseumTypeDTO typeDTO);
    List<MuseumTypeDTO> getMuseumTypes();

    List<WeatherDTO> getWeather(Integer museumID);
    void addStaticContent(Integer tourID, List<TourStaticContentDTO> staticContentDTOS);

    MuseumDTO createMuseum(MuseumDTO museum);
    TourDTO addTour(Integer museumID, FormTourDTO tourDTO) throws IOException;
}
