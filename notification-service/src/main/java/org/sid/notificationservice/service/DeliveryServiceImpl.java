package org.sid.notificationservice.service;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.dto.DeliveryDTO;
import org.sid.notificationservice.model.Delivery;

import org.sid.notificationservice.model.Notification;
import org.sid.notificationservice.repository.DeliveryRepository;
import org.sid.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.DecimalFormat;
import java.util.*;


@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private NotificationRepository notificationRepository;


    @Override
    public Delivery saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);

    }

    @Override
    public Delivery assignNotificationsToDelivery(Long deliveryId, List<Long> notificationIds) {
        // Fetch the delivery by id
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found"));

        // Fetch the notifications by their ids
        List<Notification> notifications = notificationRepository.findAllById(notificationIds);

        // Assign the delivery to each notification
        for (Notification notification : notifications) {
            notification.setDelivery(delivery);
        }

        // Save the updated notifications
        notificationRepository.saveAll(notifications);

        // Save the updated delivery
        return deliveryRepository.save(delivery);
    }


    @Override
    public String getDeliveryStateById(Long deliveryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        return optionalDelivery.map(Delivery::getState).orElse(null);
    }


    @Override
    public Delivery updateDeliveryState(Long deliveryId, String newState) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id: " + deliveryId));

        delivery.setState(newState);
        if(newState.equals("sent") ){
            delivery.setDeliveryDate(new Date());
        }


        return deliveryRepository.save(delivery);
    }



    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }


    @Override
    public List<Delivery> findDeliveriesByState(String state) {
        return deliveryRepository.findByState(state);
    }

    @Override
    public List<Delivery> findDeliveriesByStateOrNoState(){
        return deliveryRepository.findByStateOrStateIsNull("pending");
    }



    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDeliveryById(Long id) {
        deliveryRepository.deleteById(id);
    }








    @Override
    public long getTotalDeliveries() {
        return deliveryRepository.count();
    }

    @Override
    public long getCountByState(String state) {
        return deliveryRepository.countByState(state);
    }

    @Override
    public Map<String, Long> getDeliveryStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("totalDeliveries", getTotalDeliveries());
        statistics.put("sentDeliveries", getCountByState("sent"));
        statistics.put("pendingDeliveries", getCountByState("pending"));
        statistics.put("otherDeliveries", getTotalDeliveries() - statistics.get("sentDeliveries") - statistics.get("pendingDeliveries"));
        return statistics;
    }

    @Override
    public Map<String, Object> getDeliveryStatisticsWithPercentages() {
        Map<String, Long> statistics = getDeliveryStatistics();
        long totalDeliveries = statistics.get("totalDeliveries");
        Map<String, Object> statisticsWithPercentages = new HashMap<>(statistics);

        // Calculate percentages and add them to the map
        statisticsWithPercentages.put("sentDeliveriesPercentage", calculatePercentage(statistics.get("sentDeliveries"), totalDeliveries));
        statisticsWithPercentages.put("pendingDeliveriesPercentage", calculatePercentage(statistics.get("pendingDeliveries"), totalDeliveries));
        statisticsWithPercentages.put("otherDeliveriesPercentage", calculatePercentage(statistics.get("otherDeliveries"), totalDeliveries));

        // Count deliveries without notifications
        long deliveriesWithoutNotificationsCount = getDeliveriesWithoutNotificationsCount();
        double deliveriesWithoutNotificationsPercentage = totalDeliveries > 0 ?
                (deliveriesWithoutNotificationsCount / (double) totalDeliveries) * 100 : 0;
        // Format the percentage value to two decimal places
        double roundedDeliveriesWithoutNotificationsPercentage = roundToTwoDecimalPlaces(deliveriesWithoutNotificationsPercentage);
        statisticsWithPercentages.put("deliveriesWithoutNotificationsCount", deliveriesWithoutNotificationsCount);
        statisticsWithPercentages.put("deliveriesWithoutNotificationsPercentage", roundedDeliveriesWithoutNotificationsPercentage);

        // Count deliveries with notifications that don't have at least one user
        long deliveriesWithNotificationsWithoutUsersCount = getDeliveriesWithNotificationsWithoutUsersCount();
        double deliveriesWithNotificationsWithoutUsersPercentage = totalDeliveries > 0 ?
                (deliveriesWithNotificationsWithoutUsersCount / (double) totalDeliveries) * 100 : 0;
        // Format the percentage value to two decimal places
        double roundedDeliveriesWithNotificationsWithoutUsersPercentage = roundToTwoDecimalPlaces(deliveriesWithNotificationsWithoutUsersPercentage);
        statisticsWithPercentages.put("deliveriesWithNotificationsWithoutUsersCount", deliveriesWithNotificationsWithoutUsersCount);
        statisticsWithPercentages.put("deliveriesWithNotificationsWithoutUsersPercentage", roundedDeliveriesWithNotificationsWithoutUsersPercentage);

        return statisticsWithPercentages;
    }

    private double calculatePercentage(long count, long total) {
        return (count / (double) total) * 100;
    }

    // Helper method to count deliveries without notifications
    private long getDeliveriesWithoutNotificationsCount() {
        return deliveryRepository.countDeliveriesWithoutNotifications();
    }

    // Helper method to count deliveries with notifications that don't have at least one user
    private long getDeliveriesWithNotificationsWithoutUsersCount() {
        return deliveryRepository.countDeliveriesWithNotificationsWithoutUsers();
    }


    // Helper method to round a double value to two decimal places
    private double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }
}

