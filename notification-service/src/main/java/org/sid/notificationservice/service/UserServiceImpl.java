package org.sid.notificationservice.service;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sid.notificationservice.dto.SectorDTO;
import org.sid.notificationservice.dto.UserDTO;
import org.sid.notificationservice.model.Sector;
import org.sid.notificationservice.model.User;
import org.sid.notificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper; // You need to configure ModelMapper to map entities to DTOs



    /*@Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }*/


    @Override
    public List<UserDTO> getAllUsers(){
        List<User> users =userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null; // or throw an exception, depending on your error handling strategy
        }

        UserDTO dto = modelMapper.map(user, UserDTO.class);
        if (user.getSector() != null) {
            dto.setSector(modelMapper.map(user.getSector(), SectorDTO.class));
        }
        return dto;
    }


    @Override
    public void deleteUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public User updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            // Update user fields (except password for security reasons)
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            updatedUser.setSector(user.getSector()); // Update sector if necessary
            return userRepository.save(updatedUser);
        } else {
            throw new ResourceNotFoundException("User with id " + user.getId() + " not found");
        }
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }


    public List<User> findUsersBySector(Sector sector) {
        return userRepository.findBySector(sector);
    }
}
