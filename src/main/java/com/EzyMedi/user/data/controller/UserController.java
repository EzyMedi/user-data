package com.EzyMedi.user.data.controller;

import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    // Create doctor
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Get all doctors
    @GetMapping("/user/doctors")
    public List<User> getAllDoctors() {
        return userService.getAllDoctors();
    }

    // Get all patients
    @GetMapping("/user/patients")
    public List<User> getAllPatients() {
        return userService.getAllPatients();
    }

    // Get a user by ID
    @GetMapping("user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    //Delete user
    @DeleteMapping("user/{userId}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
    @PostMapping("user/{userId}/follow/{toFollowId}")
    public ResponseEntity<String> follow(@PathVariable UUID userId, @PathVariable UUID toFollowId) {
        return userService.follow(userId, toFollowId);
    }

    @PostMapping("user/{userId}/follow/{toUnfollowId}")
    public ResponseEntity<String> unfollow(@PathVariable UUID userId, @PathVariable UUID toUnfollowId) {
        return userService.unfollow(userId, toUnfollowId);
    }

    @PostMapping("user/{userId}/getFollowers")
    public List<User> getFollowers(@PathVariable UUID userId) {
        return userService.getFollowers(userId);
    }
}
