package museum.service.services.implementation;

import museum.service.repositories.UserRepository;
import museum.service.services.AdminInfoService;
import org.springframework.stereotype.Service;

@Service
public class AdminInfoServiceImpl implements AdminInfoService
{
    private final UserWatcherService userWatcherService;
    private final UserRepository userRepository;

    public AdminInfoServiceImpl(UserWatcherService userWatcherService, UserRepository userRepository)
    {
        this.userWatcherService = userWatcherService;
        this.userRepository = userRepository;
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
}
