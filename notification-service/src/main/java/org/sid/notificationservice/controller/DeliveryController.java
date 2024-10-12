package org.sid.notificationservice.controller;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.dto.CreateDeliveryRequest;
import org.sid.notificationservice.dto.DeliveryRequest;
import org.sid.notificationservice.model.Delivery;
import org.sid.notificationservice.model.Notification;
import org.sid.notificationservice.service.DeliveryService;
import org.sid.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private NotificationService notificationService;


    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }


    @GetMapping("/deliveriesByState")
    public List<Delivery> getDeliveriesByState(@RequestParam String state) {
        return deliveryService.findDeliveriesByState(state);
    }

    @GetMapping("/pendingOrNoState")
    public List<Delivery> getPendingOrNoStateDeliveries() {
        return deliveryService.findDeliveriesByStateOrNoState();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        if (delivery != null) {
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{deliveryId}/state")
    public ResponseEntity<String> getDeliveryState(@PathVariable Long deliveryId) {
        String deliveryState = deliveryService.getDeliveryStateById(deliveryId);
        if (deliveryState != null) {
            return ResponseEntity.ok(deliveryState);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{deliveryId}/state")
    public ResponseEntity<Delivery> updateDeliveryState(@PathVariable Long deliveryId, @RequestParam String newState) {
        Delivery updatedDelivery = deliveryService.updateDeliveryState(deliveryId, newState);
        return new ResponseEntity<>(updatedDelivery, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery savedDelivery = deliveryService.saveDelivery(delivery);
        return new ResponseEntity<>(savedDelivery, HttpStatus.CREATED);
    }

    @PostMapping("/{deliveryId}/notifications")
    public ResponseEntity<Delivery> assignNotificationsToDelivery(
            @PathVariable Long deliveryId,
            @RequestBody List<Long> notificationIds) {
        Delivery updatedDelivery = deliveryService.assignNotificationsToDelivery(deliveryId, notificationIds);
        return new ResponseEntity<>(updatedDelivery, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDeliveryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }








    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getDeliveryStatistics() {
        Map<String, Object> statistics = deliveryService.getDeliveryStatisticsWithPercentages();
        return ResponseEntity.ok().body(statistics);
    }
}
