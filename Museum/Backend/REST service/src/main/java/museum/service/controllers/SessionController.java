package museum.service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class SessionController
{
    @GetMapping("login_status")
    public ResponseEntity checkSessionStatus()
    {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("logout")
    public void logout(HttpSession session)
    {
        session.invalidate();
    }
}
