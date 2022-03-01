package museum.service.services.implementation;

import lombok.extern.slf4j.Slf4j;
import museum.service.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileServiceFSImpl implements FileService
{
    @Value("${static_resources_path}")
    private String resourcesPath;

    private Path root;

    @PostConstruct
    private void postConstruct()
    {
        this.root = Paths.get(resourcesPath);
        try
        {
            if(Files.exists(root) == false)
            {
                Files.createDirectories(root);
            }
        }
        catch (IOException e)
        {
            log.warn("File service could not initialize upload directory: " , e);
            throw new RuntimeException("Could not initialize file upload directory");
        }
    }

    @Override
    public void save(MultipartFile file, String fileName) throws IOException
    {
        Files.copy(file.getInputStream(), this.root.resolve(fileName));
    }

    @Override
    public Resource load(String filename) throws MalformedURLException
    {
        Path file = root.resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        if(resource.exists() || resource.isReadable())
        {
            return resource;
        }
        else
        {
            throw new RuntimeException("Could not read the file!");
        }
    }
}
