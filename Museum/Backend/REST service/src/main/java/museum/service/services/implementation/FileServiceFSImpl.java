package museum.service.services.implementation;

import museum.service.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceFSImpl implements FileService
{
    private final Path root = Paths.get("uploads");

    public FileServiceFSImpl()
    {
        try
        {
            if(Files.exists(root) == false)
            {
                Files.createDirectories(root);
            }
        }
        catch (IOException e)
        {
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
