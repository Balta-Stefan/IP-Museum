package museum.service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class TemplateController
{
    @GetMapping("login")
    public String getLoginPage(@RequestParam(required = false) Boolean error)
    {
        if(error != null && error == true)
        {
            return "loginFailed";
        }
        return "login";
    }
}
