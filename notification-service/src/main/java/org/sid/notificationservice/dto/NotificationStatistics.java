package org.sid.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class NotificationStatistics {
    private int totalNotifications;
    private int notificationsWithSentDelivery;
    private int notificationsWithPendingDelivery;
    private int notificationsWithNoDeliveryState;
    private int notificationsWithNoDelivery;
    private int notificationsWithNoUser;
    private int notificationsWithUsersNoDelivery;

}
