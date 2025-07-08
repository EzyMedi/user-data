package com.EzyMedi.user.data.controller;

import com.EzyMedi.user.data.model.Doctor;
import com.EzyMedi.user.data.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.EzyMedi.
import java.util.List;
import java.util.UUID;

@RestController
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;
    // Create doctor
    @PostMapping("/doctor")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Get all doctors
    @GetMapping("/doctor")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get a doctor by ID
    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable UUID id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update doctor by ID
    @PutMapping("doctor/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable UUID id, @RequestBody Doctor updatedDoctor) {
        return doctorRepository.findById(id)
                .map(existing -> {
                    if (updatedDoctor.getFullName() != null) existing.setFullName(updatedDoctor.getFullName());
                    if (updatedDoctor.getGender() != null) existing.setGender(updatedDoctor.getGender());
                    if (updatedDoctor.getEmail() != null) existing.setEmail(updatedDoctor.getEmail());
                    if (updatedDoctor.getPhone() != null) existing.setPhone(updatedDoctor.getPhone());
                    if (updatedDoctor.getExperience() != null) existing.setExperience(updatedDoctor.getExperience());
                    return ResponseEntity.ok(doctorRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // Follow another doctor
    @PostMapping("/doctor/{followerId}/follow/{followeeId}")
    public ResponseEntity<String> followDoctor(@PathVariable UUID followerId, @PathVariable UUID followeeId) {
        if (followerId.equals(followeeId)) {
            return ResponseEntity.badRequest().body("A doctor cannot follow themselves.");
        }

        Doctor follower = doctorRepository.findById(followerId).orElse(null);
        Doctor followee = doctorRepository.findById(followeeId).orElse(null);

        if (follower == null || followee == null) {
            return ResponseEntity.notFound().build();
        }

        if (!follower.getFollowedDoctors().contains(followee)) {
            follower.getFollowedDoctors().add(followee);
            doctorRepository.save(follower);
            return ResponseEntity.ok("Doctor successfully followed another doctor.");
        } else {
            return ResponseEntity.badRequest().body("Doctor is already following this doctor.");
        }
    }
    @PostMapping("/notify-all-followers")
    public ResponseEntity<String> notifyAllFollowers(@RequestBody News news) {


}
