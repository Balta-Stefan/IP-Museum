package museum.service.security;

import museum.service.models.CustomUserDetails;
import museum.service.models.entities.UserEntity;
import museum.service.repositories.UserRepository;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<UserEntity>
{
    private final UserRepository userRepository;

    public AuditorAwareImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> getCurrentAuditor()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            return userRepository.findById(userDetails.getId());
        }
        return Optional.empty();
    }
}
