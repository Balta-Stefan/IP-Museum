package museum.service.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreAuthenticationLoggingFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String remoteUser = request.getRemoteUser();
        String userIP = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        int remotePort = request.getRemotePort();
        String requestedURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        String authenticationType = request.getAuthType();

        String contentType = request.getContentType();
        long bodySize = request.getContentLengthLong();

        List<String> cookiePairs = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for (Cookie c : request.getCookies())
            {
                cookiePairs.add(c.getName() + "-" + c.getValue());
            }
        }

        String logID = UUID.randomUUID().toString();
        request.getSession().setAttribute("Log ID", logID);

        log.info("Log id: {}" +
                ", User: {}, " +
                "IP: {}, " +
                "remote host: {}, " +
                "port: {}, " +
                "requested URI: {}," +
                " method: {}, " +
                "auth type: {}, " +
                "content type: {}, " +
                "body size: {}, " +
                "cookies: {}", logID, remoteUser, userIP, remoteHost, remotePort, requestedURI, httpMethod, authenticationType, contentType, bodySize, cookiePairs);


        filterChain.doFilter(request, response);
    }
}
