package com.EzyMedi.user.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue
    private UUID doctorId;
    @Column
    private String fullName;
    @Column
    private String gender;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String experience;
    @Column
    private String address;
    // Doctors followed by this doctor
    @ManyToMany
    @JoinTable(
            name = "doctor_follows_doctor",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id")
    )
    private List<Doctor> followedDoctors;

    // Doctors who follow this doctor
    @ManyToMany(mappedBy = "followedDoctors")
    private List<Doctor> doctorFollowers;

    // Patients who follow this doctor
    @ManyToMany(mappedBy = "subscribingDoctors")
    private List<Patient> patientFollowers;

}
