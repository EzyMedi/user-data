package com.EzyMedi.user.data.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class DoctorDTO {
    private UUID doctorDtoId;

    private String fullName;

    private String email;

    private String address;

}
