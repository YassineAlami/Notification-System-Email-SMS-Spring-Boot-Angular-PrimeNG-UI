package org.sid.notificationservice.controller;


import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.UserNotificationDelivery;
import org.sid.notificationservice.service.UserNotificationDeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/user-notification-deliveries")
public class UserNotificationDeliveryController {
    private final UserNotificationDeliveryService userNotificationDeliveryService;


    @GetMapping
    public ResponseEntity<List<UserNotificationDelivery>> getAllUserNotificationDeliveries() {
        List<UserNotificationDelivery> userNotificationDeliveries = userNotificationDeliveryService.getAllUserNotificationDeliveries();
        return ResponseEntity.ok(userNotificationDeliveries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserNotificationDelivery> getUserNotificationDeliveryById(@PathVariable("id") Long id) {
        UserNotificationDelivery userNotificationDelivery = userNotificationDeliveryService.getUserNotificationDeliveryById(id);
        return ResponseEntity.ok(userNotificationDelivery);
    }

    @PostMapping
    public ResponseEntity<UserNotificationDelivery> addUserNotificationDelivery(@RequestBody UserNotificationDelivery userNotificationDelivery) {
        UserNotificationDelivery newUserNotificationDelivery = userNotificationDeliveryService.addUserNotificationDelivery(userNotificationDelivery);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserNotificationDelivery);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserNotificationDelivery(@PathVariable("id") Long id) {
        userNotificationDeliveryService.deleteUserNotificationDelivery(id);
        return ResponseEntity.noContent().build();
    }

    // Add other CRUD endpoints as needed
}
