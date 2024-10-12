package org.sid.notificationservice.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notifications")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userRecipient;*/

    /*@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private Sector sectorRecipient;*/


    @ManyToMany
    @JoinTable(name = "notification_user",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    //private String type;

    @Column(length = 1000)
    //@Column(nullable = false)
    private String content;

    /*@ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    private Template template;*/

    @ManyToOne
    private NotificationType type;

    private Date creationDate;


    /*@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<NotificationDelivery> notificationDeliveries;


    @OneToMany(mappedBy = "notification")
    private List<UserNotificationDelivery> userNotificationDeliveries;*/


    @ManyToOne
    @JoinColumn(name = "delivery_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Delivery delivery;

    /*@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;*/


}