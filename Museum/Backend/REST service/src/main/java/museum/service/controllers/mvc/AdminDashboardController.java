package museum.service.controllers.mvc;

import museum.service.models.entities.AccesstokenEntity;
import museum.service.repositories.AccessTokensRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDashboardController
{
    private final AccessTokensRepository tokensRepository;

    private final String tokenValidFlag = "T";

    public AdminDashboardController(AccessTokensRepository tokensRepository)
    {
        this.tokensRepository = tokensRepository;
    }

    @GetMapping("/{token}")
    public String login(@PathVariable String token, HttpSession session)
    {
        Optional<AccesstokenEntity> tokenEntity = tokensRepository.findById(UUID.fromString(token));
        if(tokenEntity.isEmpty())
        {
            return "Forbidden";
        }

        session.setAttribute(tokenValidFlag, true);

        return "redirect:/admin";
    }

    @GetMapping
    public String getAdminDashboard(HttpSession session)
    {
        Object valid = session.getAttribute(tokenValidFlag);
        if(valid != null && ((Boolean)valid) == true)
        {
            return "admin-dashboard";
        }
        return "Forbidden";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();

        return "index";
    }
}
