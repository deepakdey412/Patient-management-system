package com.pm.patientservice.service.impl;

import billing.BillingServiceGrpc;
import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.EmailAlreadyExistException;
import com.pm.patientservice.exception.PatientNotFondException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.mapper.DtoMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.IPatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final DtoMapper dtoMapper;

    public PatientServiceImpl(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, DtoMapper dtoMapper) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if (patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists: " + patientRequestDto.getEmail());
        }

        Patient savedPatient = patientRepository.save(dtoMapper.mapToEntity(patientRequestDto));

        billingServiceGrpcClient.createBillingAccount(savedPatient.getId().toString() , savedPatient.getName(), savedPatient.getEmail());

        return dtoMapper.mapToDto(savedPatient);
    }

    @Override
    public List<PatientResponseDto> getAll() {
        List<Patient> patientsList = patientRepository.findAll();
        List<PatientResponseDto> patientResponseDtoList = new ArrayList<>();// we can also use stream
        for (Patient patient : patientsList) {
            patientResponseDtoList.add(dtoMapper.mapToDto(patient));
        }
        return patientResponseDtoList;
    }

    @Override
    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto) {

        Patient foundPatient = patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFondException("Patient not found...with the id : " + id)
        );
        if (patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail() , id)) {
            throw new EmailAlreadyExistException("Email already exists: " + patientRequestDto.getEmail());
        }

        foundPatient.setName(patientRequestDto.getName());
        foundPatient.setEmail(patientRequestDto.getEmail());
        foundPatient.setAddress(patientRequestDto.getAddress());
        foundPatient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient savedPatient = patientRepository.save(foundPatient);

        return dtoMapper.mapToDto(savedPatient);
    }

    @Override
    public void deletePatient(UUID id) {

        Patient foundPatient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFondException("Patient not found with id: " + id));

        patientRepository.delete(foundPatient);
    }

}
