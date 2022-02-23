package museum.service.security;

import museum.service.exceptions.UnauthorizedException;
import museum.service.models.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


public class MvcTokenAuthFilter extends GenericFilterBean
{
    //@Value("${admin_authentication_token_query_param_name}")
    private final String admin_authentication_token_query_param_name = "token";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        CustomUserDetails user = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getIsLoggedIntoAdminApp() == true)
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // no token found, check if the user is trying to log in
        if(servletRequest.getParameter(admin_authentication_token_query_param_name) != null)
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // no authenticated flag nor query param token found, the user cannot proceed
        throw new UnauthorizedException();
    }
}
