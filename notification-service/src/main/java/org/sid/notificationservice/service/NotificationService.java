package org.sid.notificationservice.service;
import org.sid.notificationservice.dto.NotificationDTO;
import org.sid.notificationservice.dto.NotificationStatistics;
import org.sid.notificationservice.model.*;

import java.util.List;
import java.util.Map;

public interface NotificationService {

    /*List<Notification> getAllNotifications();*/
    List<NotificationDTO> getAllNotifications();
    List<NotificationDTO> getAllNotificationsFiltered();

    List<Notification> findNotificationsByDeliveryId(Long deliveryId);

    Notification createNotification(Notification notification);

    Notification addOrUpdateDeliveryToNotification(Long notificationId, Delivery delivery);

    Notification addRecipientToNotification(Long notificationId, User user);

    Notification addUsersFromSectorToNotification(Long notificationId, Sector sector);

    String generateNotificationContent(NotificationType notificationType, Map<String, String> dynamicValues);







    long getTotalNotifications();
    long getCountByNotificationType(String notificationTypeName);
    Map<String, Object> getNotificationStatistics();



    NotificationStatistics getStatisticsForMonth(String monthYear);
}
