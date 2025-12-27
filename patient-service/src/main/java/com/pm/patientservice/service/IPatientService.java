package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

import java.util.List;
import java.util.UUID;

public interface IPatientService {
    PatientResponseDto createPatient(PatientRequestDto patientRequestDto);
    List<PatientResponseDto> getAll();
    PatientResponseDto updatePatient(UUID id , PatientRequestDto patientRequestDto);
    void deletePatient(UUID id);

}
