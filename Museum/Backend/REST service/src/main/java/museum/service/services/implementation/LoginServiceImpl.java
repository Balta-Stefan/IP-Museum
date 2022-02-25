package museum.service.services.implementation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import museum.service.exceptions.UnauthorizedException;
import museum.service.models.CustomUserDetails;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.models.entities.UserEntity;
import museum.service.models.enums.Roles;
import museum.service.models.requests.LoginDetails;
import museum.service.models.responses.LoginResponse;
import museum.service.repositories.AccessTokensRepository;
import museum.service.repositories.UserRepository;
import museum.service.services.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService
{
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final AccessTokensRepository tokensRepository;
    private final ModelMapper modelMapper;

    @Value("${jwt.duration_miliseconds}")
    private long jwt_duration_miliseconds;

    @Value("${jwt.secret}")
    private String jwt_secret;

    @Value("${admin_token.validity_hours}")
    private Integer adminTokenValidityHours;

    public LoginServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepository userRepository, AccessTokensRepository tokensRepository, ModelMapper modelMapper)
    {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.tokensRepository = tokensRepository;
        this.modelMapper = modelMapper;
    }

    private String generateAdminToken(Integer userID)
    {
        UserEntity user = userRepository.findById(userID).get();

        LocalDateTime time = LocalDateTime.now();

        AccesstokenEntity token = new AccesstokenEntity();
        token.setValid(true);
        token.setUser(user);
        token.setCreated(time);
        token.setValidUntil(time.plusHours(adminTokenValidityHours));

        token = tokensRepository.saveAndFlush(token);

        return token.getToken().toString();
    }

    public LoginResponse refreshLogin(CustomUserDetails userDetails)
    {
        LoginResponse response = modelMapper.map(userDetails, LoginResponse.class);
        if(userDetails.getRole().equals(Roles.ADMIN))
        {
            response.setAdminToken(this.generateAdminToken(userDetails.getId()));
        }

        return response;
    }


    @Override
    public LoginResponse login(LoginDetails loginDetails)
    {
        try
        {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginDetails.getUsername(),
                                    loginDetails.getPassword())
                    );
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

            LoginResponse response = modelMapper.map(userDetails, LoginResponse.class);
            response.setJwt(this.generateJwt(userDetails));

            if(userDetails.getRole().equals(Roles.ADMIN))
            {
                response.setAdminToken(this.generateAdminToken(userDetails.getId()));
            }

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
