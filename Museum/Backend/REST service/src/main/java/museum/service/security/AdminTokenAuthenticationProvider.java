package museum.service.security;

import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.enums.Roles;
import museum.service.repositories.AccessTokensRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AdminTokenAuthenticationProvider implements AuthenticationProvider
{
    private final AccessTokensRepository tokensRepository;

    public AdminTokenAuthenticationProvider(AccessTokensRepository tokensRepository)
    {
        this.tokensRepository = tokensRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String token = (String)authentication.getCredentials();

        Optional<AccesstokenEntity> tokenEntityOpt = tokensRepository.findById(UUID.fromString(token));

        if(tokenEntityOpt.isEmpty())
        {
            throw new BadCredentialsException("Invalid token");
        }

        AccesstokenEntity tokenEntity = tokenEntityOpt.get();
        if(LocalDateTime.now().isAfter(tokenEntity.getValidUntil()))
        {
            throw new BadCredentialsException("Token expired.");
        }

        Roles userRole = tokenEntity.getUser().getRole();



        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), token, List.of(new SimpleGrantedAuthority(userRole.name())));
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
