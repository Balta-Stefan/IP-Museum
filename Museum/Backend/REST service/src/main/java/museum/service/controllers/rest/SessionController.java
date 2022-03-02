package museum.service.controllers.rest;

import museum.service.exceptions.UnauthorizedException;
import museum.service.models.CustomUserDetails;
import museum.service.models.requests.LoginDetails;
import museum.service.models.responses.LoginResponse;
import museum.service.services.UserSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController
{
    private final UserSessionService userSessionService;

    public SessionController(UserSessionService userSessionService)
    {
        this.userSessionService = userSessionService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginDetails loginDetails, Authentication authentication)
    {
        return userSessionService.login(loginDetails);
    }

    @GetMapping("/status")
    public LoginResponse checkStatus(Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        return userSessionService.refreshLogin(userDetails);
    }
}
