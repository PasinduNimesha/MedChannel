package com.example.medchannel.service;

import com.example.medchannel.dto.DoctorDTO;
import com.example.medchannel.dto.ResponseDTO;

import java.util.List;

public interface DoctorService {
    ResponseDTO<DoctorDTO> saveDoctor(DoctorDTO doctorDTO);

    DoctorDTO getDoctor(String id);

    ResponseDTO<DoctorDTO> updateDoctor(DoctorDTO doctorDTO);

    ResponseDTO<DoctorDTO> deleteDoctor(String id);

    List<DoctorDTO> getAllDoctors();

}
