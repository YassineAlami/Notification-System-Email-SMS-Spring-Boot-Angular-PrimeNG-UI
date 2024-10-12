package org.sid.notificationservice.repository;
import org.sid.notificationservice.model.NotificationType;
import org.sid.notificationservice.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByNotificationType(NotificationType notificationType);
}
