package org.sid.notificationservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_notification_delivery")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserNotificationDelivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Notification notification;

    private boolean delivered;

    private LocalDateTime deliveryTimestamp;
}
