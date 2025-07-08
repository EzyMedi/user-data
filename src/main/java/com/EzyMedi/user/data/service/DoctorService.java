package com.EzyMedi.user.data.service;

import com.EzyMedi.user.data.dto.DoctorDTO;
import com.EzyMedi.user.data.model.Doctor;
import com.EzyMedi.user.data.repository.DoctorRepository;
import com.EzyMedi.user.data.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patient;

    @PostMapping("/doctor")
    public DoctorDTO createDoctor(@RequestBody Doctor doctor) {
        doctorRepository.save(doctor);
        return convertToDoctorDto(doctor);
    }
    public static DoctorDTO convertToDoctorDto(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorDtoId(doctor.getDoctorId());
        dto.setFullName(doctor.getFullName());
        dto.setEmail(doctor.getEmail());
        dto.setAddress(doctor.getAddress());
        return dto;
    }
}
