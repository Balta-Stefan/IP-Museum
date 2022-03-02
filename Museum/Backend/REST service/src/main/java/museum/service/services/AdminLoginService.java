package museum.service.services;

import museum.service.models.CustomUserDetails;

public interface AdminLoginService
{
    CustomUserDetails loginAdmin(String token);
}
