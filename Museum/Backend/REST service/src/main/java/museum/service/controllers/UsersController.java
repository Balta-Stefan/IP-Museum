package museum.service.controllers;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.AccessTokenDTO;
import museum.service.models.DTOs.UserDTO;
import museum.service.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/{id}/token")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public AccessTokenDTO generateAccessToken(@PathVariable(name = "id") Integer userID)
    {
        return userService.generateAccessToken(userID);
    }
}
