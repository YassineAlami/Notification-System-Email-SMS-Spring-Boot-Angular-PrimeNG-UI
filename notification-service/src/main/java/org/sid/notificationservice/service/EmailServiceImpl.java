package org.sid.notificationservice.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.dto.EmailRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{
    SendGrid sendGrid;

    @Override
    public Response sendEmail(EmailRequest emailRequest){
        Mail mail = new Mail(new Email("m.yassine.alami@gmail.com"),
                emailRequest.getSubject(), new Email(emailRequest.getTo()),
                new Content("text/plain", emailRequest.getBody()));
        mail.setReplyTo(new Email("ahemd.ali.alami111@gmail.com"));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
        return response;
    }
}

