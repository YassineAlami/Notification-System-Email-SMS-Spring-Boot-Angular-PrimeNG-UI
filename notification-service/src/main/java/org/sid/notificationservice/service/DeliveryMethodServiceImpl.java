package org.sid.notificationservice.service;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.DeliveryMethod;
import java.util.List;

import org.sid.notificationservice.repository.DeliveryMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
@Transactional
public class DeliveryMethodServiceImpl implements DeliveryMethodService {
    private final DeliveryMethodRepository deliveryMethodRepository;


    @Override
    public List<DeliveryMethod> getAllDeliveryMethods() {
        return deliveryMethodRepository.findAll();
    }

    @Override
    public DeliveryMethod getDeliveryMethodById(Long id) {
        return deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Delivery method not found with ID: " + id));
    }

    @Override
    public DeliveryMethod createDeliveryMethod(DeliveryMethod deliveryMethod) {
        return deliveryMethodRepository.save(deliveryMethod);
    }

    @Override
    public void deleteDeliveryMethod(Long id) {
        deliveryMethodRepository.deleteById(id);
    }
}
