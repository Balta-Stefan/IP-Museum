package museum.service.services.implementation;

import museum.service.models.CustomUserDetails;
import museum.service.models.entities.UserEntity;
import museum.service.repositories.UserRepository;
import museum.service.services.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService
{
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
        {
            throw new UsernameNotFoundException("Username not found");
        });

        CustomUserDetails userDetails = new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getActive(),
                user.getRole()
        );

        return userDetails;
    }
}
