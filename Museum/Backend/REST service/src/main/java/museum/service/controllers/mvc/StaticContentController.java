package museum.service.controllers.mvc;

import museum.service.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/static")
public class StaticContentController
{
    private final FileService fileService;

    public StaticContentController(FileService fileService)
    {
        this.fileService = fileService;
    }

    @GetMapping
    public String getPage()
    {
        return "StaticContent";
    }

    @PostMapping
    public void uploadFiles(@RequestParam("firstName") String firstName, @RequestParam("file") MultipartFile[] files)
    {
        System.out.println("Ime je: " + firstName);
        for(MultipartFile f : files)
        {
            System.out.println("Name: " + f.getName());
            System.out.println("Type: " + f.getContentType());
        }
    }
}
