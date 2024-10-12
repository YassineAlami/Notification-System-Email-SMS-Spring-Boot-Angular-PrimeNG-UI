package org.sid.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.notificationservice.model.DeliveryMethod;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DeliveryDTO {

    private Long id;
    private String name;
    private String state;
    private DeliveryMethod deliveryMethod;

    private Date creationDate;
    private Date deliveryDate;
    private Date estimatedDeliveryDate;

}