package com.EzyMedi.user.data.controller;

import com.EzyMedi.user.data.constants.Role;
import com.EzyMedi.user.data.dto.UserDTO;
import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.model.UserCredential;
import com.EzyMedi.user.data.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    // Create doctor
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserCredential credential, @RequestParam Role role) {
        log.info("Controller with role {}", role);
        return userService.createUser(credential, role);
    }
    @PostMapping("/login")
    public String login(@RequestBody UserCredential credential){
        return userService.login(credential);
    }
    // Get all doctors
    @GetMapping("/doctors/get")
    public List<User> getAllDoctors() {
        return userService.getAllDoctors();
    }

    // Get all patients
    @GetMapping("/patients/get")
    public List<User> getAllPatients() {
        return userService.getAllPatients();
    }

    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a user by ID
    @GetMapping("/{userId}/get")
    public User getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    //Delete user
    @DeleteMapping("/{userId}/delete")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }
    //Update user
    @PutMapping("/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @PostMapping("/{userId}/follow/{toFollowId}")
    public ResponseEntity<String> follow(@PathVariable UUID userId, @PathVariable UUID toFollowId) {
        return userService.follow(userId, toFollowId);
    }

    @PostMapping("/{userId}/unfollow/{toUnfollowId}")
    public ResponseEntity<String> unfollow(@PathVariable UUID userId, @PathVariable UUID toUnfollowId) {
        return userService.unfollow(userId, toUnfollowId);
    }

    @GetMapping("/getFollowers/{userId}")
    public List<UserDTO> getFollowers(@PathVariable UUID userId) {
        return userService.getFollowers(userId);
    }

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Welcome to Helen "+request.getSession().getId();
    }

    public CsrfToken getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return token;
    }

}
