package museum.service.services;

import org.springframework.core.io.InputStreamSource;

import javax.mail.MessagingException;

public interface EmailSender
{
    void sendEmail(String receiver, String title, String message, InputStreamSource attachments) throws MessagingException;
}
