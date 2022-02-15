package museum.service.services.implementation;

import museum.service.services.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
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
    public void sendEmail(String receiver, String title, String message, InputStreamSource attachment) throws MessagingException
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(title);
        helper.setFrom(senderMail);
        helper.setTo(receiver);
        helper.setReplyTo(senderMail);
        helper.setText(message);
        helper.addAttachment("Karta.pdf", attachment);

        mailSender.send(mimeMessage);
        /*MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                int c = 1;
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
                mimeMessage.setFrom(new InternetAddress(senderMail));
                mimeMessage.setSubject(title);
                mimeMessage.setText(message);

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                for(InputStreamSource pdfStream : attachments)
                {
                    helper.addAttachment("Prilog.pdf", pdfStream, "application/pdf");
                    c++;
                }
            }
        };

        mailSender.send(preparator);*/
    }
}
