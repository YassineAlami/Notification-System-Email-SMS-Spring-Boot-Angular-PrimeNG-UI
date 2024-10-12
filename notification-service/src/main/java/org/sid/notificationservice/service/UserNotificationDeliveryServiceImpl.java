package org.sid.notificationservice.service;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.UserNotificationDelivery;
import org.sid.notificationservice.repository.UserNotificationDeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserNotificationDeliveryServiceImpl implements UserNotificationDeliveryService {
    private final UserNotificationDeliveryRepository userNotificationDeliveryRepository;



    @Override
    public UserNotificationDelivery addUserNotificationDelivery(UserNotificationDelivery userNotificationDelivery) {
        return userNotificationDeliveryRepository.save(userNotificationDelivery);
    }

    @Override
    public List<UserNotificationDelivery> getAllUserNotificationDeliveries() {
        return userNotificationDeliveryRepository.findAll();
    }

    @Override
    public UserNotificationDelivery getUserNotificationDeliveryById(Long id) {
        return userNotificationDeliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Notification Delivery not found with ID: " + id));
    }

    @Override
    public void deleteUserNotificationDelivery(Long id) {
        userNotificationDeliveryRepository.deleteById(id);
    }

    // Add other methods as needed
}
