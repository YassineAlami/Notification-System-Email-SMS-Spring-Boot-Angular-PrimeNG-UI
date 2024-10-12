package org.sid.notificationservice.service;

import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.model.Template;

import java.util.List;

public interface TemplateService {
    Template createTemplate(Template template);
    List<Template> getAllTemplates();

    List<Template> getTemplatesByNotificationType(NotificationType notificationType);

    /*Template getTemplateById(Long id);
    Template updateTemplate(Long id, Template template);
    void deleteTemplate(Long id);*/
}
