package museum.service.controllers.rest;

import museum.service.models.CustomUserDetails;
import museum.service.models.requests.LoginDetails;
import museum.service.models.responses.LoginResponse;
import museum.service.services.UserSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public LoginResponse checkSessionStatus(@RequestBody @Valid LoginDetails loginDetails, Authentication authentication)
    {
        if(authentication != null)
        {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            return userSessionService.refreshLogin(userDetails);
        }
        else
        {
            return userSessionService.login(loginDetails);
        }
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        String jwt = (String)authentication.getCredentials();

        this.userSessionService.logout(userDetails);
    }
}
