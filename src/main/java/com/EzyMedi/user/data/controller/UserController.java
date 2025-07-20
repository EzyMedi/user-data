package com.EzyMedi.user.data.controller;

import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // Create doctor
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
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
    public List<User> getFollowers(@PathVariable UUID userId) {
        return userService.getFollowers(userId);
    }
}
