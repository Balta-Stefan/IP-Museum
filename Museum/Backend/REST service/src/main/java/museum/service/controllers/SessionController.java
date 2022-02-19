package museum.service.controllers;

import museum.service.models.CustomUserDetails;
import museum.service.models.DTOs.LoginDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/")
public class SessionController
{
    @PostMapping("login")
    public LoginDetailsDTO checkSessionStatus(Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails)(authentication.getPrincipal());

        LoginDetailsDTO loginDetailsDTO = new LoginDetailsDTO(userDetails.getId(), userDetails.getRole());

        return loginDetailsDTO;
    }

    @PostMapping("logout")
    public void logout(HttpSession session)
    {
        session.invalidate();
    }
}
