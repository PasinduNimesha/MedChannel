package com.example.medchannel.controller;

import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.dto.PhysicianDTO;
import com.example.medchannel.service.PhysicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/physician")
@RequiredArgsConstructor
public class PhysicianController {

    private final PhysicianService PhysicianService;

    @GetMapping("/all")
    public ResponseEntity<List<PhysicianDTO>> getAllPhysicians() {
        return new ResponseEntity<>(PhysicianService.getAllPhysicians(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicianDTO> getPhysician(@PathVariable("id") String id) {
        return new ResponseEntity<>(PhysicianService.getPhysician(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PhysicianDTO>> createPhysician(@RequestBody PhysicianDTO PhysicianDTO) {
        return new ResponseEntity<>(PhysicianService.savePhysician(PhysicianDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<PhysicianDTO>> updatePhysician(@RequestBody PhysicianDTO PhysicianDTO) {
        return new ResponseEntity<>(PhysicianService.updatePhysician(PhysicianDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<PhysicianDTO>> deletePhysician(@PathVariable("id") String id) {
        return new ResponseEntity<>(PhysicianService.deletePhysician(id), HttpStatus.OK);
    }
}