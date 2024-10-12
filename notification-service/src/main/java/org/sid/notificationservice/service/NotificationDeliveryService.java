package org.sid.notificationservice.service;


import org.sid.notificationservice.model.NotificationDelivery;

import java.util.List;

public interface NotificationDeliveryService {
    NotificationDelivery findById(Long id);

    List<NotificationDelivery> findAll();

    NotificationDelivery save(NotificationDelivery notificationDelivery);

    void deleteById(Long id);
}
