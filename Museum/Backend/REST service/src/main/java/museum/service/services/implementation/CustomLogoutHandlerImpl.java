package museum.service.services.implementation;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomLogoutHandlerImpl implements LogoutHandler
{
    private final UserWatcherService userWatcherService;

    public CustomLogoutHandlerImpl(UserWatcherService userWatcherService)
    {
        this.userWatcherService = userWatcherService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        int x = userWatcherService.logout();
        //authentication.setAuthenticated(false);
        //request.getSession().invalidate();

        System.out.println("Logout: num of currently logged in users: " + x);
    }
}
