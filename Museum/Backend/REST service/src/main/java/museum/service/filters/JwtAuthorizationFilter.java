package museum.service.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import museum.service.models.CustomUserDetails;
import museum.service.models.enums.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter
{

    @Value("${jwt.authorization_header.name}")
    private String authorizationHeaderName;

    @Value("${jwt.authorization_header.prefix}")
    private String authorizationHeaderPrefix;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authorizationHeader = request.getHeader(authorizationHeaderName);
        if(authorizationHeader == null || authorizationHeader.startsWith(authorizationHeaderPrefix) == false)
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(authorizationHeaderPrefix, "");
        try
        {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            Roles userRole = Roles.valueOf(claims.get("role", String.class));
            CustomUserDetails customUserDetails = new CustomUserDetails(Integer.valueOf(claims.getId()), claims.getSubject(), null, true, userRole);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
