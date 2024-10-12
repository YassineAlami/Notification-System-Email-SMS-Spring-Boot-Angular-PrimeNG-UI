package org.sid.notificationservice.repository;
import org.sid.notificationservice.model.Notification;
import org.sid.notificationservice.model.NotificationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByDeliveryId(Long deliveryId);
    long countByType(NotificationType type);



    @Query("SELECT COUNT(n) FROM Notification n WHERE SIZE(n.users) > 0")
    long countNotificationsWithUsers();

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.delivery IS NOT NULL")
    long countNotificationsWithDelivery();


    // Stats Radar

    int countByCreationDateBetween(Date startDate, Date endDate);

    /*int countByDelivery_StateAndCreationDateBetween(String state, Date startDate, Date endDate);*/

    int countByDeliveryIsNullAndCreationDateBetween(Date startDate, Date endDate);

    int countByUsersIsEmptyAndCreationDateBetween(Date startDate, Date endDate);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.users IS NOT EMPTY AND n.delivery IS NULL AND n.creationDate BETWEEN :startDate AND :endDate")
    int countByUsersIsNotEmptyAndDeliveryIsNullAndCreationDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int countByDelivery_StateAndCreationDateBetween(String state, Date startDate, Date endDate);

}
