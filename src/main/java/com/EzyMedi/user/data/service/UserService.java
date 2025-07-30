package com.EzyMedi.user.data.service;

import com.EzyMedi.user.data.constants.Role;
import com.EzyMedi.user.data.dto.UserDTO;
import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.model.UserCredential;
import com.EzyMedi.user.data.repository.UserCredentialRepository;
import com.EzyMedi.user.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCredentialRepository userCredentialRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> createUser(UserCredential credential, Role role) {
        try {
            // Optional: check if the user credential already exists
            if (userCredentialRepository.existsByAccountName(credential.getAccountName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("User already exists");
            }
//            String passwordBefore = credential.getPasswordHash();
//            credential.setPasswordHash(passwordEncoder.encode(passwordBefore));
            log.info("Saving credential to db: {}", credential.getAccountName());
            userCredentialRepository.save(credential);

            User user = new User(credential.getCredentialId(), role);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User creation failed: " + e.getMessage());
        }
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
                    if (updatedUser.getGender() != null) existing.setGender(updatedUser.getGender());
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
        if (userId == toFollowId) {
            return ResponseEntity.badRequest().body("User can't follow himself.");
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
    public List<UserDTO> getFollowers(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }

        return user.getFollowers()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public UserDTO convertToDto (User user){
        return new UserDTO(user.getUserId(),user.getEmail(),user.getRole());
    }

}