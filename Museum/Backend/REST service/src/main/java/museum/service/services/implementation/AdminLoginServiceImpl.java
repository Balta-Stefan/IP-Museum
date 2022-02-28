package museum.service.services.implementation;

import museum.service.exceptions.UnauthorizedException;
import museum.service.models.CustomUserDetails;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.entities.UserEntity;
import museum.service.models.enums.Roles;
import museum.service.repositories.AccessTokensRepository;
import museum.service.security.AdminTokenAuthenticationProvider;
import museum.service.services.AdminLoginService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
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
        UserEntity userEntity = accessToken.getUser();

        CustomUserDetails userDetails = new CustomUserDetails(userEntity.getUserId(),
                userEntity.getUsername(),
                null,
                userEntity.getActive(),
                userEntity.getRole(), null);

        if(LocalDateTime.now().isAfter(accessToken.getValidUntil()) || accessToken.getValid() == false)
        {
            throw new UnauthorizedException();
        }

        try
        {
            Roles userRole = accessToken.getUser().getRole();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, token, null);

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
