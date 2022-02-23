package museum.service.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService
{
    void save(MultipartFile file) throws IOException;
    Resource load(String filename) throws MalformedURLException;
}
