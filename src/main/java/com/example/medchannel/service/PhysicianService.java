package com.example.medchannel.service;

import com.example.medchannel.dto.PhysicianDTO;
import com.example.medchannel.dto.ResponseDTO;

import java.util.List;

public interface PhysicianService {
    ResponseDTO<PhysicianDTO> savePhysician(PhysicianDTO PhysicianDTO);

    PhysicianDTO getPhysician(String id);

    ResponseDTO<PhysicianDTO> updatePhysician(PhysicianDTO PhysicianDTO);

    ResponseDTO<PhysicianDTO> deletePhysician(String id);

    List<PhysicianDTO> getAllPhysicians();

}
