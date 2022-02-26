package museum.service.controllers.mvc;

import museum.service.services.AdminLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDashboardController
{
    private final AdminLoginService adminLoginService;

    public AdminDashboardController(AdminLoginService adminLoginService)
    {
        this.adminLoginService = adminLoginService;
    }

    @GetMapping
    public String getAdminPanel()
    {
        return "admin-dashboard";
    }

    @GetMapping("/login")
    public String login(@RequestParam("token") String token)
    {
        if(adminLoginService.loginAdmin(token) == true)
        {
            return "redirect:/admin";
        }

        return "unauthorized";
    }



    @GetMapping("/invalidate_session")
    public String logout(HttpSession session)
    {
        session.invalidate();

        return "redirect:/";
    }


    @GetMapping("/users")
    public String getUsersPanel()
    {
        return "users-panel";
    }

    @GetMapping("/museums")
    public String getMuseumsPanel()
    {
        return "museums-panel";
    }

    @GetMapping("/museums/{museumID}/tours")
    public String getToursPanel(@PathVariable Integer museumID, Model model)
    {
        return "museum-tours-panel";
    }
}
