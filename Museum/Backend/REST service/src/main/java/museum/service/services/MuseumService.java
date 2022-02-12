package museum.service.services;

import museum.service.models.UserDTO;

public interface MuseumService
{
    void buyTicket(Integer museumID, UserDTO buyer);
}
