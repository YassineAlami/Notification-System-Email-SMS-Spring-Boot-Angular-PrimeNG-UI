package org.sid.notificationservice.service;

import org.sid.notificationservice.dto.DeliveryDTO;
import org.sid.notificationservice.model.Delivery;
import org.sid.notificationservice.model.Notification;

import java.util.List;
import java.util.Map;

public interface DeliveryService {
    Delivery saveDelivery(Delivery delivery);


    Delivery assignNotificationsToDelivery(Long deliveryId, List<Long> notificationIds);


    String getDeliveryStateById(Long deliveryId);

    Delivery updateDeliveryState(Long deliveryId, String newState);


    List<Delivery> getAllDeliveries();

    List<Delivery> findDeliveriesByState(String state);
    List<Delivery> findDeliveriesByStateOrNoState();

    Delivery getDeliveryById(Long id);
    void deleteDeliveryById(Long id);







    long getTotalDeliveries();
    long getCountByState(String state);
    Map<String, Long> getDeliveryStatistics();

    Map<String, Object> getDeliveryStatisticsWithPercentages();


}
