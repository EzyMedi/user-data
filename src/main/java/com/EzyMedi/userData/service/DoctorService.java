package com.EzyMedi.userData.service;

import com.EzyMedi.userData.repository.DoctorRepository;
import com.EzyMedi.userData.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patient;
}
