package org.sid.notificationservice.service;
import org.sid.notificationservice.model.NotificationType;

import java.util.List;
import java.util.Map;

public interface NotificationTypeService {

    // Method to retrieve all notification types
    List<NotificationType> getAllNotificationTypes();

    // Method to create a new notification type
    NotificationType createNotificationType(NotificationType notificationType);

}
