package com.medilabo.microservice_patient.services;

import com.medilabo.microservice_patient.exceptions.PatientDoesNotExistException;
import com.medilabo.microservice_patient.models.Patient;
import com.medilabo.microservice_patient.repositories.PatientRepository;
import com.medilabo.microservice_patient.utils.PatientMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient addNewPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient findPatientById(Long id) throws PatientDoesNotExistException {
        return patientExistById(id);
    }

    public Patient updatePatientById(Long id, Patient patient) throws PatientDoesNotExistException {
        Patient patientExistById = patientExistById(id);
        return patientRepository.save(PatientMapper.map(patientExistById, patient));
    }

    public void deletePatientById(Long id) throws PatientDoesNotExistException {
        Optional <Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            patientRepository.deleteById(id);
        }
        throw new PatientDoesNotExistException("Patient does not exist for id: " + id);
    }

    public List <Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    private Patient patientExistById(Long id) throws PatientDoesNotExistException {
        Optional <Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            return patient.get();
        }
        throw new PatientDoesNotExistException("Patient does not exist for id: " + id);
    }

}
