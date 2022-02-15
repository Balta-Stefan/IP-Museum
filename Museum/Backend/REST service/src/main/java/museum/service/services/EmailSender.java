package museum.service.services;

import org.springframework.core.io.InputStreamResource;

import java.util.List;

public interface EmailSender
{
    void sendEmail(String receiver, String title, String message, List<InputStreamResource> attachments);
}
