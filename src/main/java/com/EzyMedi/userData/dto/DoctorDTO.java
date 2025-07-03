package com.EzyMedi.userData.dto;

import java.util.UUID;

public class DoctorDTO {
    private UUID doctorDtoId;

    private String fullName;

    private String email;

    private String address;

    public UUID getId() { return doctorDtoId; }

    public void setId(UUID id) { this.doctorDtoId = id; }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
