package com.EzyMedi.user.data.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;
@Data
public class PatientDTO {
    private UUID patientId;
    private String fullName;
    private String gender;
    private String birthDate;
    private String email;
    private String phone;
}
