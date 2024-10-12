package org.sid.notificationservice.controller;

import lombok.AllArgsConstructor;
import org.sid.notificationservice.dto.UserDTO;
import org.sid.notificationservice.model.User;
import org.sid.notificationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    /*@GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }*/


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }





    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // Update mapping for specific ID
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id); // Ensure ID matches path variable
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser); // Return updated user in response
    }

    @DeleteMapping("/{id}") // Delete mapping for specific ID
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Return empty 204 No Content on success
    }
}
