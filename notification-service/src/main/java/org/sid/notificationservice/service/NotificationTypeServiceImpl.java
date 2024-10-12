package org.sid.notificationservice.service;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class NotificationTypeServiceImpl implements NotificationTypeService {
    private NotificationTypeRepository notificationTypeRepository;


    @Override
    public List<NotificationType> getAllNotificationTypes() {
        return notificationTypeRepository.findAll();
    }

    @Override
    public NotificationType createNotificationType(NotificationType notificationType) {
        return notificationTypeRepository.save(notificationType);
    }


}

