package org.sid.notificationservice.controller;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.model.Template;
import org.sid.notificationservice.repository.NotificationTypeRepository;
import org.sid.notificationservice.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final NotificationTypeRepository notificationTypeRepository;


    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        Template createdTemplate = templateService.createTemplate(template);
        return new ResponseEntity<>(createdTemplate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }


    @GetMapping("/byType")
    public List<Template> getTemplatesByNotificationType(@RequestParam Long notificationTypeId) {
        NotificationType notificationType = notificationTypeRepository.findById(notificationTypeId)
                .orElseThrow(() -> new NotFoundException("Notification type not found with ID: " + notificationTypeId));

        return templateService.getTemplatesByNotificationType(notificationType);
    }
}
