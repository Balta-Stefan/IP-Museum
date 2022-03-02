package museum.service.filters;

import lombok.extern.slf4j.Slf4j;
import museum.service.models.CustomUserDetails;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Component
//@Order(Ordered.LOWEST_PRECEDENCE)
public class PostAuthenticationLoggingFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        System.out.println("Post auth filter");
        String logID = (String)request.getSession().getAttribute("Log ID");


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String user = "anonymous";

        if(authentication != null)
        {
            Object principal = authentication.getPrincipal();

            if(principal instanceof CustomUserDetails userDetails)
            {
                user = userDetails.getId().toString();
            }
            else
            {
                user = principal.toString();
            }
        }
        log.info("User: {} is associated with log ID: {}", user, logID);

        filterChain.doFilter(request, response);
    }
}
