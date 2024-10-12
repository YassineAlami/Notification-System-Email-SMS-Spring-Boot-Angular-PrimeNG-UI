package org.sid.notificationservice.service;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sid.notificationservice.dto.DeliveryDTO;
import org.sid.notificationservice.dto.NotificationDTO;
import org.sid.notificationservice.dto.NotificationStatistics;
import org.sid.notificationservice.exception.NotificationNotFoundException;
import org.sid.notificationservice.model.*;
import org.sid.notificationservice.repository.NotificationRepository;
import org.sid.notificationservice.repository.NotificationTypeRepository;
import org.sid.notificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final UserService userService;
    private final TemplateService templateService;
    private final DeliveryService deliveryService;
    private ModelMapper modelMapper; // You need to configure ModelMapper to map entities to DTOs



    /*@Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }*/


    @Override
    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getAllNotificationsFiltered() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .filter(notification -> notification.getDelivery() == null /*|| !"sent".equals(notification.getDelivery().getState())*/)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private NotificationDTO convertToDTO(Notification notification) {
        if (notification == null) {
            return null; // or throw an exception, depending on your error handling strategy
        }

        NotificationDTO dto = modelMapper.map(notification, NotificationDTO.class);
        if (notification.getDelivery() != null) {
            dto.setDelivery(modelMapper.map(notification.getDelivery(), DeliveryDTO.class));
        }
        return dto;
    }


    @Override
    public List<Notification> findNotificationsByDeliveryId(Long deliveryId) {
        return notificationRepository.findByDeliveryId(deliveryId);
    }







    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification addOrUpdateDeliveryToNotification(Long notificationId, Delivery delivery) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            if (notification.getDelivery() == null) {
                // If no delivery is assigned, set the delivery
                notification.setDelivery(delivery);
            } else {
                // If a delivery is already assigned, update it
                notification.getDelivery().setName(delivery.getName());
                notification.getDelivery().setDeliveryMethod(delivery.getDeliveryMethod());
                // Update other delivery attributes as needed
            }
            return notificationRepository.save(notification);
        } else {
            // Handle notification not found
            throw new NotificationNotFoundException("Notification not found with id: " + notificationId);
        }
    }

    @Override
    @Transactional
    public Notification addRecipientToNotification(Long notificationId, User user) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found with ID: " + notificationId));

        notification.getUsers().add(user);
        user.getNotifications().add(notification); // Update inverse relationship
        //userRepository.save(user); // Save the user to update the inverse relationship

        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public Notification addUsersFromSectorToNotification(Long notificationId, Sector sector) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found with ID: " + notificationId));

        // Fetch users belonging to the specified sector within the transactional context
        List<User> usersFromSector = userService.findUsersBySector(sector);

        // Add each user from the sector to the notification
        for (User user : usersFromSector) {
            notification.getUsers().add(user);
            user.getNotifications().add(notification); // Update inverse relationship
        }
        return notificationRepository.save(notification);
    }

    @Override
    public String generateNotificationContent(NotificationType notificationType, Map<String, String> dynamicValues) {
        Template template = templateService.getTemplatesByNotificationType(notificationType).get(0);
        String templateContent = template.getBody();

        // Replace placeholders with dynamic values
        for (Map.Entry<String, String> entry : dynamicValues.entrySet()) {
            String placeholder = entry.getKey();
            String dynamicValue = entry.getValue();
            templateContent = templateContent.replaceAll(placeholder, dynamicValue);
        }

        return templateContent;
    }










    @Override
    public long getTotalNotifications() {
        return notificationRepository.count();
    }

    @Override
    public long getCountByNotificationType(String notificationTypeName) {
        NotificationType notificationType = notificationTypeRepository.findByName(notificationTypeName);
        if (notificationType != null) {
            return notificationRepository.countByType(notificationType);
        }
        return 0;
    }

    @Override
    public Map<String, Object> getNotificationStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        long totalNotifications = getTotalNotifications();
        statistics.put("totalNotifications", totalNotifications);

        // Count notifications with at least one user
        long notificationsWithUserCount = getNotificationsWithUserCount();
        double notificationsWithUserPercentage = totalNotifications > 0 ?
                (notificationsWithUserCount / (double) totalNotifications) * 100 : 0;
        // Format the percentage value to two decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        double roundedNotificationsWithUserPercentage = Double.parseDouble(df.format(notificationsWithUserPercentage));
        statistics.put("notificationsWithUserCount", notificationsWithUserCount);
        statistics.put("notificationsWithUserPercentage", roundedNotificationsWithUserPercentage);

        // Count notifications with a delivery
        long notificationsWithDeliveryCount = getNotificationsWithDeliveryCount();
        double notificationsWithDeliveryPercentage = totalNotifications > 0 ?
                (notificationsWithDeliveryCount / (double) totalNotifications) * 100 : 0;
        // Format the percentage value to two decimal places
        double roundedNotificationsWithDeliveryPercentage = Double.parseDouble(df.format(notificationsWithDeliveryPercentage));
        statistics.put("notificationsWithDeliveryCount", notificationsWithDeliveryCount);
        statistics.put("notificationsWithDeliveryPercentage", roundedNotificationsWithDeliveryPercentage);

        // Count notifications by type
        List<NotificationType> notificationTypes = notificationTypeRepository.findAll();
        for (NotificationType notificationType : notificationTypes) {
            long count = getCountByNotificationType(notificationType.getName());
            double percentage = totalNotifications > 0 ? (count / (double) totalNotifications) * 100 : 0;
            // Format the percentage value to two decimal places
            double roundedPercentage = Double.parseDouble(df.format(percentage));
            statistics.put(notificationType.getName() + "NotificationsCount", count);
            statistics.put(notificationType.getName() + "NotificationsPercentage", roundedPercentage);
        }

        return statistics;
    }


    // Method to count notifications with at least one user
    private long getNotificationsWithUserCount() {
        return notificationRepository.countNotificationsWithUsers();
    }

    // Method to count notifications with a delivery
    private long getNotificationsWithDeliveryCount() {
        return notificationRepository.countNotificationsWithDelivery();
    }






    // Parse a string like "06-2020" to a Date object range
    private Date[] getStartAndEndDate(String monthYear) {
        String[] parts = monthYear.split("-");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        Calendar start = Calendar.getInstance();
        start.set(year, month - 1, 1, 0, 0, 0);

        Calendar end = Calendar.getInstance();
        end.set(year, month, 1, 0, 0, 0);
        end.add(Calendar.DATE, -1);

        return new Date[] { start.getTime(), end.getTime() };
    }


    @Override
    public NotificationStatistics getStatisticsForMonth(String monthYear) {
        Date[] dates = getStartAndEndDate(monthYear);
        Date startDate = dates[0];
        Date endDate = dates[1];

        NotificationStatistics stats = new NotificationStatistics();

        // Total notifications for the given month
        stats.setTotalNotifications(notificationRepository.countByCreationDateBetween(startDate, endDate));

        // Notifications with 'sent' delivery
        stats.setNotificationsWithSentDelivery(notificationRepository.countByDelivery_StateAndCreationDateBetween("sent", startDate, endDate));

        // Notifications with 'pending' delivery
        stats.setNotificationsWithPendingDelivery(notificationRepository.countByDelivery_StateAndCreationDateBetween("pending", startDate, endDate));

        // Notifications with '-' delivery
        stats.setNotificationsWithNoDelivery(notificationRepository.countByDelivery_StateAndCreationDateBetween("-", startDate, endDate));
        stats.setNotificationsWithNoDeliveryState(notificationRepository.countByDelivery_StateAndCreationDateBetween("-", startDate, endDate)); // <-- Added this line


        // Notifications with no delivery
        stats.setNotificationsWithNoDelivery(notificationRepository.countByDeliveryIsNullAndCreationDateBetween(startDate, endDate));

        // Notifications with no user
        stats.setNotificationsWithNoUser(notificationRepository.countByUsersIsEmptyAndCreationDateBetween(startDate, endDate));

        // Notifications with users but no delivery
        stats.setNotificationsWithUsersNoDelivery(notificationRepository.countByUsersIsNotEmptyAndDeliveryIsNullAndCreationDateBetween(startDate, endDate));

        return stats;
    }



}
