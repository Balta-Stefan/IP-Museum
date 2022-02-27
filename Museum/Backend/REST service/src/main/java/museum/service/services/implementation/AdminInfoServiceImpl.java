package museum.service.services.implementation;

import museum.service.models.entities.MuseumEntity;
import museum.service.models.entities.UserEntity;
import museum.service.repositories.MuseumsRepository;
import museum.service.repositories.UserRepository;
import museum.service.services.AdminInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
public class AdminInfoServiceImpl implements AdminInfoService
{
    private final UserWatcherService userWatcherService;
    private final UserRepository userRepository;
    private final MuseumsRepository museumsRepository;
    private final PasswordEncoder passwordEncoder;

    private final int passwordLengthBytes = 16;

    private final ModelMapper modelMapper;

    public AdminInfoServiceImpl(UserWatcherService userWatcherService, UserRepository userRepository, MuseumsRepository museumsRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper)
    {
        this.userWatcherService = userWatcherService;
        this.userRepository = userRepository;
        this.museumsRepository = museumsRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public int getNumberOfLoggedInUsers()
    {
        return this.userWatcherService.getNumOfLoggedInUsers();
    }

    @Override
    public long getNumberOfRegisteredUsers()
    {
        return this.userRepository.count();
    }

    @Override
    public int[] getLoginsPerHours()
    {
        return this.userWatcherService.getUserLoginsPerHour();
    }

    @Override
    public Page<UserEntity> getUsers(boolean onlyInactive, int pageNumber, int pageSize)
    {
        Page<UserEntity> users;

        Pageable page = PageRequest.of(pageNumber, pageSize);

        if(onlyInactive == true)
        {
            users = this.userRepository.findAllByActiveIsFalse(page);
        }
        else
        {
            users = this.userRepository.findAll(page);
        }

        return users;
    }

    public boolean changeUserActivityStatus(int userID, boolean active)
    {
        Optional<UserEntity> userEntityOpt = userRepository.findById(userID);

        if(userEntityOpt.isEmpty())
        {
            return false;
        }
        UserEntity userEntity = userEntityOpt.get();
        userEntity.setActive(active);


        userEntity = userRepository.save(userEntity);

        return true;
    }

    @Override
    public String changeUserPassword(int userID)
    {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userID);
        if(userEntityOptional.isEmpty())
        {
            return "Traženi korisnik ne postoji";
        }
        UserEntity user = userEntityOptional.get();

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[this.passwordLengthBytes];
        random.nextBytes(bytes);

        byte[] base64Password = Base64.getEncoder().encode(bytes);

        String stringPass = new String(base64Password);

        String encodedPass = this.passwordEncoder.encode(stringPass);
        user.setPassword(encodedPass);
        userRepository.save(user);

        return stringPass;
    }

    @Override
    public Page<MuseumEntity> getMuseums(int pageNumber, int pageSize)
    {
        return this.museumsRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
