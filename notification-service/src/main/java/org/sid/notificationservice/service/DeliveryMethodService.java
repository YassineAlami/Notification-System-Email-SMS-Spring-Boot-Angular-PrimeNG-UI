package org.sid.notificationservice.service;
import org.sid.notificationservice.model.DeliveryMethod;
import java.util.List;


public interface DeliveryMethodService {
    List<DeliveryMethod> getAllDeliveryMethods();
    DeliveryMethod getDeliveryMethodById(Long id);
    DeliveryMethod createDeliveryMethod(DeliveryMethod deliveryMethod);
    void deleteDeliveryMethod(Long id);
}
