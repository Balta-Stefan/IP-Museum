package museum.service.controllers;

import museum.service.exceptions.NotFoundException;
import museum.service.models.CustomUserDetails;
import museum.service.models.UserDTO;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.entities.UserEntity;
import museum.service.repositories.AccessTokensRepository;
import museum.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class SessionController
{
    private final UserRepository userRepository;
    private final AccessTokensRepository tokensRepository;

    @Value("${token.validity_days}")
    private Integer tokenValidity_days;

    public SessionController(UserRepository userRepository, AccessTokensRepository tokensRepository)
    {
        this.userRepository = userRepository;
        this.tokensRepository = tokensRepository;
    }

    @PostMapping("/login")
    public UserDTO checkSessionStatus(Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());


        // generate a new access token
        LocalDateTime tokenCreationTimestamp = LocalDateTime.now();
        UserEntity userEntity = userRepository.findById(userDetails.getId()).orElseThrow(NotFoundException::new);
        AccesstokenEntity token = new AccesstokenEntity();
        token.setUser(userEntity);
        token.setCreated(tokenCreationTimestamp);
        token.setValidUntil(tokenCreationTimestamp.plusDays(tokenValidity_days));
        token.setValid(true);

        token = tokensRepository.saveAndFlush(token);

        userDetails.getUserDTO().setToken(token.getToken().toString());

        return userDetails.getUserDTO();
    }

    @PostMapping("/logout")
    public void logout(HttpSession session)
    {
        session.invalidate();
    }
}
