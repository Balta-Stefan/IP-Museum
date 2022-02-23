package museum.service.controllers.mvc;

import museum.service.models.CustomUserDetails;
import museum.service.models.entities.AccesstokenEntity;
import museum.service.repositories.AccessTokensRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDashboardController
{
    private final AccessTokensRepository tokensRepository;

    public AdminDashboardController(AccessTokensRepository tokensRepository)
    {
        this.tokensRepository = tokensRepository;
    }


    @GetMapping
    public String login(@RequestParam(required = false) String token, HttpSession session, Authentication authentication)
    {
        // check if the user is already logged in
        CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();


        if(user.getIsLoggedIntoAdminApp())
        {
            return "admin-dashboard";
        }
        else if(token == null)
        {
            return "Forbidden";
        }

        // token is supplied, determine whether it is valid
        Optional<AccesstokenEntity> tokenEntity = tokensRepository.findById(UUID.fromString(token));
        if(tokenEntity.isEmpty())
        {
            return "Forbidden";
        }

        user.setIsLoggedIntoAdminApp(true);

        return "redirect:/admin";
    }



    @GetMapping("/invalidate_session")
    public String logout(HttpSession session)
    {
        session.invalidate();

        return "redirect:/";
    }


    @GetMapping("/users")
    public String getUsers()
    {
        return "users-panel";
    }
}
