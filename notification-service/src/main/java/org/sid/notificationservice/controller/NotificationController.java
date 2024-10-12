package org.sid.notificationservice.controller;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.dto.NotificationDTO;
import org.sid.notificationservice.dto.NotificationStatistics;
import org.sid.notificationservice.exception.DeliveryNotFoundException;
import org.sid.notificationservice.exception.NotificationNotFoundException;
import org.sid.notificationservice.model.*;
import org.sid.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;


    /*@GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }*/

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // Endpoint to get all notifications with filtering
    @GetMapping("/filtered")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsFiltered() {
        List<NotificationDTO> filteredNotifications = notificationService.getAllNotificationsFiltered();
        return ResponseEntity.ok(filteredNotifications);
    }


    @GetMapping("/delivery/{deliveryId}")
    public List<Notification> getNotificationsByDeliveryId(@PathVariable Long deliveryId) {
        return notificationService.findNotificationsByDeliveryId(deliveryId);
    }



    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }


    @PutMapping("/{notificationId}/delivery")
    public ResponseEntity<String> addOrUpdateDeliveryToNotification(
            @PathVariable Long notificationId,
            @RequestBody Delivery delivery) {
        try {
            notificationService.addOrUpdateDeliveryToNotification(notificationId, delivery);
            return ResponseEntity.ok("Delivery added/updated successfully to the notification.");
        } catch (NotificationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DeliveryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delivery not found.");
        }
    }



    @PostMapping("/{notificationId}/addRecipient")
    public Notification addRecipientToNotification(@PathVariable Long notificationId, @RequestBody User user) {
        return notificationService.addRecipientToNotification(notificationId, user);
    }

    @PostMapping("/{notificationId}/addUsersFromSector")
    public ResponseEntity<?> addUsersFromSectorToNotification(@PathVariable Long notificationId,
                                                              @RequestBody Sector sector) {
        try {
            notificationService.addUsersFromSectorToNotification(notificationId, sector);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/generate-content")
    public ResponseEntity<String> generateNotificationContent(@RequestBody GenerateNotificationContentRequest request) {
        // Call the service method to generate the notification content
        String content = notificationService.generateNotificationContent(request.getNotificationType(), request.getDynamicValues());

        return ResponseEntity.ok(content);
    }






    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getNotificationStatistics() {
        Map<String, Object> statistics = notificationService.getNotificationStatistics();
        return ResponseEntity.ok().body(statistics);
    }


    // Endpoint to get Radar statistics
    @GetMapping("/global-statistics")
    public NotificationStatistics getNotificationStatistics(@RequestParam String monthYear) {
        return notificationService.getStatisticsForMonth(monthYear);
    }
}
