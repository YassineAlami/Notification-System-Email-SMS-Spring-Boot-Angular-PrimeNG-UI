package org.sid.notificationservice.repository;
import org.sid.notificationservice.model.UserNotificationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationDeliveryRepository extends JpaRepository<UserNotificationDelivery, Long> {

}
