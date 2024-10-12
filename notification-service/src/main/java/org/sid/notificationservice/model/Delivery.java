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
@Table(name = "deliveries")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String state = "-";

    private Date creationDate;
    private Date deliveryDate;
    private Date estimatedDeliveryDate;


    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethod deliveryMethod;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();


    @PrePersist
    private void prePersist() {
        if (this.state == null) {
            this.state = "-";
        }
       /* if (this.deliveryDate == null) {
            this.deliveryDate = "-";
        }*/
    }

}
