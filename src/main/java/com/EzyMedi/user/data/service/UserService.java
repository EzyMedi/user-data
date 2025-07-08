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
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllDoctors() {
        return userRepository.findAllUsersByRole(Role.DOCTOR);
    }

    public List<User> getAllPatients() {
        return userRepository.findAllUsersByRole(Role.PATIENT);
    }

    public ResponseEntity<User> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
}