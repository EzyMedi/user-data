package com.EzyMedi.user.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue
    private UUID customerId;
    @Column
    private String fullName;
    @Column
    private String gender;
    @Column
    private String birthDate;
    @Column
    private String email;
    @Column
    private String phone;
    // Doctors this patient follows
    @ManyToMany
    @JoinTable(
            name = "patient_follows_doctor",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> subscribingDoctors;

    public void updatePatientData(Patient patient) {
        if (patient.getFullName() != null) {
            this.fullName = patient.getFullName();
        }
        if (patient.getGender() != null) {
            this.gender = patient.getGender();
        }
        if (patient.getBirthDate() != null) {
            this.birthDate = patient.getBirthDate();
        }
        if (patient.getEmail() != null) {
            this.email = patient.getEmail();
        }
        if (patient.getPhone() != null) {
            this.phone = patient.getPhone();
        }
    }
}
