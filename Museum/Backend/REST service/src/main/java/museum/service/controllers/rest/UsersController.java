package museum.service.controllers.rest;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.UserDTO;
import museum.service.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController
{
    private final UserService userService;

    public UsersController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody @Valid UserDTO user, Authentication authentication)
    {
        CustomUserDetails userDetails = null;
        if(authentication != null)
        {
            userDetails = (CustomUserDetails)(authentication.getPrincipal());
        }

        return userService.createUser(user, Optional.ofNullable(userDetails));
    }
}
