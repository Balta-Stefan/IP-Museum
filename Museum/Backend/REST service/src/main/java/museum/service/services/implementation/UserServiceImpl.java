package museum.service.services.implementation;

import museum.service.exceptions.ConflictException;
import museum.service.exceptions.ForbiddenException;
import museum.service.exceptions.NotFoundException;
import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.AccessTokenDTO;
import museum.service.models.DTOs.UserDTO;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.entities.UserEntity;
import museum.service.models.enums.Roles;
import museum.service.repositories.AccessTokensRepository;
import museum.service.repositories.UserRepository;
import museum.service.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final AccessTokensRepository accessTokensRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("token.validity_days")
    private Integer token_validity_days;

    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository, AccessTokensRepository accessTokensRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.accessTokensRepository = accessTokensRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userToCreate, Optional<CustomUserDetails> requester)
    {
        if(userToCreate.getRole().equals(Roles.ADMIN))
        {
            CustomUserDetails creator = requester.orElseThrow(ForbiddenException::new);

            if(creator.getRole().equals(Roles.ADMIN) == false)
            {
                throw new ForbiddenException();
            }
        }

        if(userRepository.findByEmailAndUsername(userToCreate.getEmail(), userToCreate.getUsername()).isPresent())
        {
            throw new ConflictException();
        }

        UserEntity user = modelMapper.map(userToCreate, UserEntity.class);

        user.setUserId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);


        user = userRepository.saveAndFlush(user);
        entityManager.refresh(user);

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public AccessTokenDTO grantAccessToken(UserDTO user)
    {
        UserEntity userEntity = userRepository.findById(user.getUserID()).orElseThrow(NotFoundException::new);

        AccesstokenEntity newToken = new AccesstokenEntity();
        newToken.setToken(null);
        newToken.setUser(userEntity);
        newToken.setCreated(LocalDateTime.now());
        newToken.setValidUntil(LocalDateTime.now().plusDays(token_validity_days));
        newToken.setValid(true);

        newToken = accessTokensRepository.saveAndFlush(newToken);
        entityManager.refresh(newToken);

        return modelMapper.map(newToken, AccessTokenDTO.class);
    }
}
