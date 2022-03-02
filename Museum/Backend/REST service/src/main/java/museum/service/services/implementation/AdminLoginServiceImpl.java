package museum.service.services.implementation;

import lombok.extern.slf4j.Slf4j;
import museum.service.exceptions.UnauthorizedException;
import museum.service.models.CustomUserDetails;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.entities.UserEntity;
import museum.service.models.enums.Roles;
import museum.service.repositories.AccessTokensRepository;
import museum.service.services.AdminLoginService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class AdminLoginServiceImpl implements AdminLoginService
{
    private final AccessTokensRepository tokensRepository;

    public AdminLoginServiceImpl(AccessTokensRepository tokensRepository)
    {
        this.tokensRepository = tokensRepository;
    }

    @Override
    public CustomUserDetails loginAdmin(String token)
    {
        AccesstokenEntity accessToken = tokensRepository.findById(UUID.fromString(token)).orElseThrow(UnauthorizedException::new);
        UserEntity userEntity = accessToken.getUser();

        CustomUserDetails userDetails = new CustomUserDetails(userEntity.getUserId(),
                userEntity.getUsername(),
                null,
                userEntity.getActive(),
                userEntity.getRole());

        if(LocalDateTime.now().isAfter(accessToken.getValidUntil()) || accessToken.getValid() == false)
        {
            throw new UnauthorizedException();
        }

        try
        {
            Roles userRole = accessToken.getUser().getRole();

            accessToken.setValid(false);
            accessToken = tokensRepository.saveAndFlush(accessToken);
        }
        catch(Exception e)
        {
            log.warn("Admin login service has thrown an exception: ", e);
            throw new UnauthorizedException();
        }
        return userDetails;
    }
}
