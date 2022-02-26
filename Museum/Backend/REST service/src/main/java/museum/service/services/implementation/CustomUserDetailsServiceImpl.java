package museum.service.services.implementation;

import museum.service.models.CustomUserDetails;
import museum.service.models.UserDTO;
import museum.service.models.entities.UserEntity;
import museum.service.services.CustomUserDetailsService;
import museum.service.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CustomUserDetailsServiceImpl(UserRepository userRepository, ModelMapper modelMapper)
    {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity user = userRepository.findByUsernameAndActiveTrue(username).orElseThrow(() ->
        {
            throw new UsernameNotFoundException("Username not found");
        });


		UserDTO u = modelMapper.map(user, UserDTO.class);

        return new CustomUserDetails(u.getUserID(), u.getUsername(), u.getPassword(), u.getActive(), u.getRole(), null);
    }
}
