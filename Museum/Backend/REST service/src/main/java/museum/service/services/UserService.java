package museum.service.services;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.AccessTokenDTO;
import museum.service.models.DTOs.UserDTO;

import java.util.Optional;

public interface UserService
{
    UserDTO createUser(UserDTO userToCreate, Optional<CustomUserDetails> requester);
    AccessTokenDTO generateAccessToken(Integer userID);
}
