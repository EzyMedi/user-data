package com.EzyMedi.user.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @PostMapping("/patient")
    Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @GetMapping("/patient/{patientId}")
    ResponseEntity<Patient> getPatientById(@PathVariable UUID patientId) {
        return patientRepository.findById(patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient")
    List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @PutMapping("patient/{patientId}")
    Patient updatePatientData(@PathVariable UUID patientId, @RequestBody Patient patient) {
        Patient patientData = patientRepository.findById(patientId).orElse(null);
        if (patientData != null) {
            patientData.updatePatientData(patient);
            patientRepository.save(patientData);
        }
        return patientData;
    }

    @DeleteMapping("patient/{patientId}")
    void deletePatient(@PathVariable UUID id) {
        patientRepository.deleteById(id);
    }

    //Follow doctor
    @PostMapping("patient/{patientId}/follow/{doctorId}")
    public ResponseEntity<String> patientFollowDoctor(@PathVariable UUID patientId, @PathVariable UUID doctorId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (patient == null || doctor == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the doctor is already subscribed
        if (!patient.getSubscribingDoctors().contains(doctor)) {
            patient.getSubscribingDoctors().add(doctor);
            patientRepository.save(patient);
            return ResponseEntity.ok("Patient subscribed to doctor successfully.");
        } else {
            return ResponseEntity.badRequest().body("Patient already subscribed to this doctor.");
        }
    }

}
