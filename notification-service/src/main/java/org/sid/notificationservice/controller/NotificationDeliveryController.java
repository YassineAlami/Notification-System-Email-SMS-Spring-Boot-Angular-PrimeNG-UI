package org.sid.notificationservice.controller;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.NotificationDelivery;
import org.sid.notificationservice.service.NotificationDeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/notification-deliveries")
public class NotificationDeliveryController {
    private final NotificationDeliveryService notificationDeliveryService;



    @GetMapping("/{id}")
    public ResponseEntity<NotificationDelivery> getNotificationDeliveryById(@PathVariable Long id) {
        NotificationDelivery notificationDelivery = notificationDeliveryService.findById(id);
        return ResponseEntity.ok(notificationDelivery);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDelivery>> getAllNotificationDeliveries() {
        List<NotificationDelivery> notificationDeliveries = notificationDeliveryService.findAll();
        return ResponseEntity.ok(notificationDeliveries);
    }

    @PostMapping
    public ResponseEntity<NotificationDelivery> createNotificationDelivery(@RequestBody NotificationDelivery notificationDelivery) {
        NotificationDelivery createdNotificationDelivery = notificationDeliveryService.save(notificationDelivery);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotificationDelivery);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationDelivery(@PathVariable Long id) {
        notificationDeliveryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
