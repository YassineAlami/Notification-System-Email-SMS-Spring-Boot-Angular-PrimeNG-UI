package org.sid.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class GenerateNotificationContentRequest {

    private NotificationType notificationType;
    private Map<String, String> dynamicValues;

}