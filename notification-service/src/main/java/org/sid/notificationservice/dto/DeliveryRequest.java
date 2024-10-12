package org.sid.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.notificationservice.model.Delivery;
import org.sid.notificationservice.model.Notification;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DeliveryRequest {
    private Delivery delivery;
    private List<Notification> notifications;

}
