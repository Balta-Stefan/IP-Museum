package museum.service.services.implementation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import museum.service.exceptions.UnauthorizedException;
import museum.service.models.CustomUserDetails;
import museum.service.models.requests.LoginDetails;
import museum.service.models.responses.LoginResponse;
import museum.service.services.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService
{
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;

    @Value("${jwt.duration_miliseconds}")
    private long jwt_duration_miliseconds;

    @Value("${jwt.secret}")
    private String jwt_secret;

    public LoginServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, ModelMapper modelMapper)
    {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoginResponse login(LoginDetails loginDetails)
    {
        try
        {
            LoginResponse response = new LoginResponse();

            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginDetails.getUsername(),
                                    loginDetails.getPassword())
                    );
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

            response = modelMapper.map(userDetails, LoginResponse.class);
            response.setJwt(this.generateJwt(userDetails));

            return response;
        }
        catch(Exception e)
        {
            throw new UnauthorizedException();
        }
   }

   private String generateJwt(CustomUserDetails userDetails)
   {
       return Jwts.builder()
               .setId(userDetails.getId().toString())
               .setSubject(userDetails.getUsername())
               .claim("role", userDetails.getRole().name())
               .setExpiration(new Date(System.currentTimeMillis() + jwt_duration_miliseconds))
               .signWith(SignatureAlgorithm.HS512, jwt_secret)
               .compact();
   }
}
