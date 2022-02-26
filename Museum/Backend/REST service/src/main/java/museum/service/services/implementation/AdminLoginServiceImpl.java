package museum.service.services.implementation;

import museum.service.exceptions.UnauthorizedException;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.enums.Roles;
import museum.service.repositories.AccessTokensRepository;
import museum.service.security.AdminTokenAuthenticationProvider;
import museum.service.services.AdminLoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AdminLoginServiceImpl implements AdminLoginService
{
    private final AccessTokensRepository tokensRepository;
    private final AdminTokenAuthenticationProvider authenticationManager;

    public AdminLoginServiceImpl(AccessTokensRepository tokensRepository, AdminTokenAuthenticationProvider authenticationManager)
    {
        this.tokensRepository = tokensRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Boolean loginAdmin(String token)
    {
        AccesstokenEntity accessToken = tokensRepository.findById(UUID.fromString(token)).orElseThrow(UnauthorizedException::new);

        if(LocalDateTime.now().isAfter(accessToken.getValidUntil()) || accessToken.getValid() == false)
        {
            throw new UnauthorizedException();
        }

        try
        {
            Roles userRole = accessToken.getUser().getRole();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(token, null);

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            accessToken.setValid(false);
            accessToken = tokensRepository.saveAndFlush(accessToken);
        }
        catch(Exception e)
        {
            throw new UnauthorizedException();
        }
        return true;
    }
}
