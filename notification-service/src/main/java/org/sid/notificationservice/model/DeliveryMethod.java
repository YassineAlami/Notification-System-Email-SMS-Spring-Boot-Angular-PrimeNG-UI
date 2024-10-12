package org.sid.notificationservice.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "delivery_methods")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DeliveryMethod {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false, unique = true)
    private String name; // Name of the delivery method (e.g., Email, SMS, Push Notification)

    //@Column(nullable = false)
    private String description; // Description of the delivery method

    // Additional attributes for Email delivery method
    private String smtpHost;
    private int smtpPort;
    private String smtpUsername;
    private String smtpPassword;

    // Additional attributes for SMS delivery method
    private String smsApiUrl;
    private String smsApiKey;
    private String smsSenderId;

}