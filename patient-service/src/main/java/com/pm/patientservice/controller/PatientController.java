package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.IPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Patients", description = "APIs of patients service")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create patient")
    public ResponseEntity<PatientResponseDto> createNewPatient(
            @Validated({Default.class , CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return new ResponseEntity<>(patientResponseDto , HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all the patients")
    public ResponseEntity<List<PatientResponseDto>> getAll(){
        return ResponseEntity.ok(patientService.getAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update patient")
    public ResponseEntity<PatientResponseDto> updatethePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patientRequestDto);
        return new ResponseEntity<>(patientResponseDto , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
