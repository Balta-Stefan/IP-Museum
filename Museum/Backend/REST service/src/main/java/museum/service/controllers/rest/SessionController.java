package museum.service.controllers.rest;

import museum.service.models.CustomUserDetails;
import museum.service.models.requests.LoginDetails;
import museum.service.models.responses.LoginResponse;
import museum.service.services.LoginService;
import museum.service.services.implementation.UserWatcherService;
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
    private final LoginService loginService;
    private final UserWatcherService userWatcherService;


    /*@Value("${token.validity_days}")
    private Integer tokenValidity_days;*/

    public SessionController(LoginService loginService, UserWatcherService userWatcherService)
    {
        this.loginService = loginService;
        this.userWatcherService = userWatcherService;
    }

    @PostMapping("/login")
    public LoginResponse checkSessionStatus(@RequestBody @Valid LoginDetails loginDetails, Authentication authentication)
    {
        if(authentication != null)
        {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            return loginService.refreshLogin(userDetails);
            //return modelMapper.map(userDetails, LoginResponse.class);
        }
        else
        {
            return loginService.login(loginDetails);
        }

        /*CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());


        UserEntity userEntity = userRepository.findById(userDetails.getId()).orElseThrow(NotFoundException::new);

        // generate a new access token if this user is admin
        if(userEntity.getRole().equals(Roles.ADMIN))
        {
            LocalDateTime tokenCreationTimestamp = LocalDateTime.now();

            AccesstokenEntity token = new AccesstokenEntity();
            token.setUser(userEntity);
            token.setCreated(tokenCreationTimestamp);
            token.setValidUntil(tokenCreationTimestamp.plusDays(tokenValidity_days));
            token.setValid(true);

            token = tokensRepository.saveAndFlush(token);

            userDetails.getUserDTO().setToken(token.getToken().toString());
        }

        return userDetails.getUserDTO();*/
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        String jwt = (String)authentication.getCredentials();

        System.out.println("user logging out: " + userDetails.getId());
        System.out.println("With jwt: " + jwt);

        userWatcherService.logout();
    }
}
