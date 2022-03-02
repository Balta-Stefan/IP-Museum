package museum.service.security;

import museum.service.services.implementation.UserWatcherService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler
{
    private final UserWatcherService userWatcherService;

    public CustomLogoutSuccessHandler(UserWatcherService userWatcherService)
    {
        this.userWatcherService = userWatcherService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        this.userWatcherService.logout();
        response.sendRedirect("/");
    }
}
