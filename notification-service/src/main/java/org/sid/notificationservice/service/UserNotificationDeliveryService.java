package org.sid.notificationservice.service;

import org.sid.notificationservice.model.UserNotificationDelivery;

import java.util.List;

public interface UserNotificationDeliveryService {
    UserNotificationDelivery addUserNotificationDelivery(UserNotificationDelivery userNotificationDelivery);
    List<UserNotificationDelivery> getAllUserNotificationDeliveries();
    UserNotificationDelivery getUserNotificationDeliveryById(Long id);
    void deleteUserNotificationDelivery(Long id);
    // Add other methods as needed
}