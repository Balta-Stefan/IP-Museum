package museum.service.services.implementation;

import museum.service.services.EmailSender;
import museum.service.utilities.TourPDFCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailSenderImpl implements EmailSender
{
    @Value("${spring.mail.username}")
    private String senderMail;

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String receiver, String title, String message, List<InputStreamResource> attachments)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
                mimeMessage.setFrom(new InternetAddress(senderMail));
                mimeMessage.setSubject(title);
                mimeMessage.setText(message);

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                for(InputStreamResource pdfStream : attachments)
                {
                    helper.addAttachment(pdfStream.getDescription(), pdfStream);
                }
            }
        };

        mailSender.send(preparator);
    }
}
