package org.sid.notificationservice.service;
import lombok.AllArgsConstructor;
import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.model.Template;
import org.sid.notificationservice.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;


    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    public List<Template> getTemplatesByNotificationType(NotificationType notificationType) {
        return templateRepository.findByNotificationType(notificationType);
    }
}
