package org.sid.notificationservice.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_delivery")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class NotificationDelivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id"/*, nullable = false*/)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id"/*, nullable = false*/)
    private DeliveryMethod deliveryMethod;

    //@Column(nullable = false)
    private String status;

    //@Column(nullable = false)
    private LocalDateTime timestamp;

}
