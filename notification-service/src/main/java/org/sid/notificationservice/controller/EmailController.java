package org.sid.notificationservice.controller;

import com.sendgrid.Response;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.dto.EmailRequest;
import org.sid.notificationservice.service.EmailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {

    EmailServiceImpl emailService;


    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(EmailRequest emailRequest) {
        Response response = emailService.sendEmail(emailRequest);
        if (response != null && (response.getStatusCode() == 200 || response.getStatusCode() == 202)) {
            return new ResponseEntity<>("Email was Sent Successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to Send Email!", HttpStatus.NOT_FOUND);
    }
}
