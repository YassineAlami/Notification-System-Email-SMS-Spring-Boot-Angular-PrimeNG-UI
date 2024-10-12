package org.sid.notificationservice.controller;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.DeliveryMethod;
import org.sid.notificationservice.service.DeliveryMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/delivery-methods")
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;



    @GetMapping
    public ResponseEntity<List<DeliveryMethod>> getAllDeliveryMethods() {
        List<DeliveryMethod> deliveryMethods = deliveryMethodService.getAllDeliveryMethods();
        return ResponseEntity.ok(deliveryMethods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryMethod> getDeliveryMethodById(@PathVariable("id") Long id) {
        DeliveryMethod deliveryMethod = deliveryMethodService.getDeliveryMethodById(id);
        return ResponseEntity.ok(deliveryMethod);
    }

    @PostMapping
    public ResponseEntity<DeliveryMethod> createDeliveryMethod(@RequestBody DeliveryMethod deliveryMethod) {
        DeliveryMethod createdDeliveryMethod = deliveryMethodService.createDeliveryMethod(deliveryMethod);
        return new ResponseEntity<>(createdDeliveryMethod, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryMethod(@PathVariable("id") Long id) {
        deliveryMethodService.deleteDeliveryMethod(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
