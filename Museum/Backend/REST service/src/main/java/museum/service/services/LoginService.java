package museum.service.services;

import museum.service.models.CustomUserDetails;
import museum.service.models.requests.LoginDetails;
import museum.service.models.responses.LoginResponse;

public interface LoginService
{
    LoginResponse login(LoginDetails loginDetails);
    LoginResponse refreshLogin(CustomUserDetails userDetails);
}
