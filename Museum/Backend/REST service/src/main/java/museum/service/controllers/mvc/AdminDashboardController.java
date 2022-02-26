package museum.service.controllers.mvc;

import museum.service.models.DTOs.UserDTO;
import museum.service.models.entities.UserEntity;
import museum.service.services.AdminInfoService;
import museum.service.services.AdminLoginService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDashboardController
{
    private final AdminLoginService adminLoginService;
    private final AdminInfoService adminInfoService;
    private final ModelMapper modelMapper;

    public AdminDashboardController(AdminLoginService adminLoginService, AdminInfoService adminInfoService, ModelMapper modelMapper)
    {
        this.adminLoginService = adminLoginService;
        this.adminInfoService = adminInfoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getAdminPanel(Model model)
    {
        model.addAttribute("numOfLoggedInUsers", this.adminInfoService.getNumberOfLoggedInUsers());
        model.addAttribute("numOfRegisteredUsers", this.adminInfoService.getNumberOfRegisteredUsers());
        model.addAttribute("usersPerHours", this.adminInfoService.getLoginsPerHours());

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



    @GetMapping("/users")
    public String getUsersPanel(@RequestParam(defaultValue = "false") Boolean onlyInactive,
                                @RequestParam(defaultValue = "0") Integer pageNumber,
                                @RequestParam(defaultValue = "30") Integer pageSize,
                                Model model)
    {
        Page<UserEntity> page = this.adminInfoService.getUsers(onlyInactive, pageNumber, pageSize);
        List<UserDTO> users = page
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());


        model.addAttribute("users", users);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("onlyInactive", onlyInactive);
        model.addAttribute("totalUsers", page.getTotalElements());
        model.addAttribute("totalPages", Math.floor(page.getTotalPages()));

        return "users-panel";
    }


    @PostMapping("/users")
    public String changeUserInfo(@RequestParam(required = false) Boolean newStatus,
                                     @RequestParam(required = false) Boolean changePassword,
                                     @RequestParam(required = false) Integer userID,
                                     @RequestParam(defaultValue = "false") Boolean onlyInactive,
                                     @RequestParam(defaultValue = "0") Integer pageNumber,
                                     @RequestParam(defaultValue = "30") Integer pageSize,
                                     Model model)
    {
        String message = "";

        if(newStatus != null && userID != null)
        {
            boolean success = this.adminInfoService.changeUserActivityStatus(userID, newStatus);
            message = (success == true) ? "Izmjena statusa aktivnosti uspješna" : "Izmjena statusa aktivnosti neuspješna";
        }
        else if(changePassword != null && userID != null)
        {
            message = "Nova lozinka je: " + this.adminInfoService.changeUserPassword(userID);
        }

        model.addAttribute("message", message);
        Page<UserEntity> page = this.adminInfoService.getUsers(onlyInactive, pageNumber, pageSize);
        List<UserDTO> users = page
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("onlyInactive", onlyInactive);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalUsers", page.getTotalElements());
        model.addAttribute("totalPages", Math.floor(page.getTotalPages()));


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
