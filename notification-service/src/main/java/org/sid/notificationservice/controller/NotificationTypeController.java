package org.sid.notificationservice.controller;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.service.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/notification-types")
public class NotificationTypeController {
    private NotificationTypeService notificationTypeService;


    // Endpoint to retrieve all notification types
    @GetMapping
    public List<NotificationType> getAllNotificationTypes() {
        return notificationTypeService.getAllNotificationTypes();
    }

    // Endpoint to create a new notification type
    @PostMapping
    public NotificationType createNotificationType(@RequestBody NotificationType notificationType) {
        return notificationTypeService.createNotificationType(notificationType);
    }
}
