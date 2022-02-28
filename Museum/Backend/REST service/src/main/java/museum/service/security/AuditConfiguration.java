package museum.service.security;

import museum.service.models.entities.UserEntity;
import museum.service.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfiguration
{
    private final UserRepository userRepository;

    public AuditConfiguration(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Bean
    AuditorAware<UserEntity> auditorProvider()
    {
        return new AuditorAwareImpl(userRepository);
    }
}
