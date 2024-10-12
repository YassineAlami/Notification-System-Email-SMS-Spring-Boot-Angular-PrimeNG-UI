package org.sid.notificationservice.repository;
import org.sid.notificationservice.model.Sector;
import org.sid.notificationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findBySector(Sector sector);
}
