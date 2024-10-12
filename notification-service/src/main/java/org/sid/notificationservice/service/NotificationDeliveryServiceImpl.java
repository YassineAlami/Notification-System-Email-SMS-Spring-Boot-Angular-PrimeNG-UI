package org.sid.notificationservice.service;


import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.NotificationDelivery;
import org.sid.notificationservice.repository.NotificationDeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class NotificationDeliveryServiceImpl implements NotificationDeliveryService {
    private final NotificationDeliveryRepository notificationDeliveryRepository;



    @Override
    public NotificationDelivery findById(Long id) {
        return notificationDeliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification delivery not found with ID: " + id));
    }

    @Override
    public List<NotificationDelivery> findAll() {
        return notificationDeliveryRepository.findAll();
    }

    @Override
    public NotificationDelivery save(NotificationDelivery notificationDelivery) {
        return notificationDeliveryRepository.save(notificationDelivery);
    }

    @Override
    public void deleteById(Long id) {
        notificationDeliveryRepository.deleteById(id);
    }
}
