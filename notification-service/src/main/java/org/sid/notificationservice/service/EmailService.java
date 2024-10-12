package org.sid.notificationservice.service;

import com.sendgrid.Response;
import org.sid.notificationservice.dto.EmailRequest;

public interface EmailService {

    Response sendEmail(EmailRequest emailRequest);

}
