package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.IPatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient/api")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatientResponseDto> createNewPatient(
            @Validated({Default.class , CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return new ResponseEntity<>(patientResponseDto , HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PatientResponseDto>> getAll(){
        return ResponseEntity.ok(patientService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientResponseDto> updatethePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patientRequestDto);
        return new ResponseEntity<>(patientResponseDto , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
