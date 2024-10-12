package org.sid.notificationservice.service;
import org.sid.notificationservice.dto.UserDTO;
import org.sid.notificationservice.model.Sector;
import org.sid.notificationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.List;

public interface UserService {

    //List<User> getAllUsers();

    List<UserDTO> getAllUsers();



    void deleteUser(Long id);
    User updateUser(User user);


    User addUser(User user);
    List<User> findUsersBySector(Sector sector);
}
