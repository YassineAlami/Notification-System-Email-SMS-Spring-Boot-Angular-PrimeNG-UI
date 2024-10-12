package org.sid.notificationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.notificationservice.model.Delivery;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateDeliveryRequest {
    private Delivery delivery;
    private List<Long> notificationIds;

}
