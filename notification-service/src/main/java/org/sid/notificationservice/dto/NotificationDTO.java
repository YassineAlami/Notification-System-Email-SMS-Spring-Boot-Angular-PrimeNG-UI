package org.sid.notificationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.model.User;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class NotificationDTO {

    private Long id;
    private String content;
    private Date creationDate;
    private List<User> users;
    private NotificationType type;

    private DeliveryDTO delivery; // Include DeliveryDTO

    // Getters and setters (optional)
}