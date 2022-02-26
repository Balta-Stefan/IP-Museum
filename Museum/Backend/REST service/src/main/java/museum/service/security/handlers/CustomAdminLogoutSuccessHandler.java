package museum.service.security.handlers;

import museum.service.services.implementation.UserWatcherService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomAdminLogoutSuccessHandler implements LogoutSuccessHandler
{
    private final UserWatcherService userWatcherService;

    public CustomAdminLogoutSuccessHandler(UserWatcherService userWatcherService)
    {
        this.userWatcherService = userWatcherService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        //authentication.setAuthenticated(false);
        //request.getSession().invalidate();

        userWatcherService.logout();

        response.sendRedirect("/");
    }
}
