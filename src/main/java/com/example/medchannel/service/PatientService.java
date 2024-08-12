package com.example.medchannel.service;

import com.example.medchannel.dto.PatientDTO;
import com.example.medchannel.dto.ResponseDTO;

import java.util.List;

public interface PatientService {
    ResponseDTO<PatientDTO> savePatient(PatientDTO patientDTO);

    List<PatientDTO> getAllPatients();

    PatientDTO getPatient(String id);

    ResponseDTO<PatientDTO> updatePatient(PatientDTO patientDTO);

    ResponseDTO<PatientDTO> deletePatient(String id);
}
