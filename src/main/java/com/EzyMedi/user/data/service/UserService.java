package com.EzyMedi.user.data.service;

import com.EzyMedi.user.data.constants.Role;
import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllDoctors() {
        return userRepository.findAllUsersByRole(Role.DOCTOR);
    }

    public List<User> getAllPatients() {
        return userRepository.findAllUsersByRole(Role.PATIENT);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<User> updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id)
                .map(existing -> {
                    if (updatedUser.getFullName() != null) existing.setFullName(updatedUser.getFullName());
                    if (updatedUser.getGender() != null) existing.setGender(updatedUser.getGender());
                    if (updatedUser.getEmail() != null) existing.setEmail(updatedUser.getEmail());
                    if (updatedUser.getPhone() != null) existing.setPhone(updatedUser.getPhone());
                    return ResponseEntity.ok(userRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<String> follow( UUID userId, UUID toFollowId) {
        User user = userRepository.findById(userId).orElse(null);
        User toFollow = userRepository.findById(toFollowId).orElse(null);

        if (user == null || toFollow == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the doctor is already subscribed
        if (!user.getFollowing().contains(toFollow)) {
            user.addFollower(toFollow);
            userRepository.save(user);
            return ResponseEntity.ok("User follow successfully.");
        } else {
            return ResponseEntity.badRequest().body("User already followed.");
        }
    }

    public ResponseEntity<String> unfollow( UUID userId, UUID toUnfollowId) {
        User user = userRepository.findById(userId).orElse(null);
        User toUnfollow = userRepository.findById(toUnfollowId).orElse(null);

        if (user == null || toUnfollow == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the doctor is already subscribed
        if (user.getFollowing().contains(toUnfollow)) {
            user.removeFollower(toUnfollow);
            userRepository.save(user);
            return ResponseEntity.ok("User unfollow successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not follow this person.");
        }
    }
    public List<User> getFollowers(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null ) {
            return emptyList();
        }
        return user.getFollowers();
    }

}