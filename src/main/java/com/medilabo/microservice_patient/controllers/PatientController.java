package com.medilabo.microservice_patient.controllers;

import com.medilabo.microservice_patient.exceptions.PatientDoesNotExistException;
import com.medilabo.microservice_patient.models.Patient;
import com.medilabo.microservice_patient.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/add")
    public ResponseEntity<Patient> addNewPatient(
            @RequestBody Patient patient) {
        try {
            Patient added = patientService.addNewPatient(patient);
            return ResponseEntity.ok().body(added);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> findAllPatient() {
        List <Patient> allPatients = patientService.findAllPatients();
        return ResponseEntity.ok().body(allPatients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findPatientById(@PathVariable Long id) {
        try {
            Patient patient = patientService.findPatientById(id);
            return ResponseEntity.ok().body(patient);
        } catch (PatientDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deletePatientById(
            @PathVariable Long id
    ) {
        try {
            patientService.deletePatientById(id);
            return ResponseEntity.ok().body("Patient deleted with success.");
        } catch (PatientDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatientById(
            @PathVariable Long id,
            @RequestBody Patient patient) {
        try {
            Patient updateById = patientService.updatePatientById(id, patient);
            return ResponseEntity.ok().body(updateById);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
