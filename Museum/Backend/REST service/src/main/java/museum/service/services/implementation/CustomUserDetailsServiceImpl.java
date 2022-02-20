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
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
        {
            throw new UsernameNotFoundException("Username not found");
        });

        try
        {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            System.out.println(userDTO.toString());
            CustomUserDetails userDetails = new CustomUserDetails(userDTO);
            return userDetails;
        }
        catch(Exception e){e.printStackTrace();}



        return null;
    }
}
