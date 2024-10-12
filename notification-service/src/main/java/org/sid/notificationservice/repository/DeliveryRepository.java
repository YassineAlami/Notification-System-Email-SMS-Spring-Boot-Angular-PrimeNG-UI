package org.sid.notificationservice.repository;

import org.sid.notificationservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByState(String state);
    List<Delivery> findByStateOrStateIsNull(String state);

    long countByState(String state);



    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.notifications IS EMPTY")
    long countDeliveriesWithoutNotifications();

/*    @Query("SELECT COUNT(d) FROM Delivery d JOIN d.notifications n WHERE n.users IS EMPTY")
    long countDeliveriesWithNotificationsWithoutUsers();*/


    // Custom query to count deliveries with notifications that don't have any associated users
    @Query("SELECT COUNT(d) FROM Delivery d WHERE NOT EXISTS (SELECT n FROM d.notifications n JOIN n.users u)")
    long countDeliveriesWithNotificationsWithoutUsers();
}
