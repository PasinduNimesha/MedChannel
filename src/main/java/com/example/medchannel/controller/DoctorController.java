package com.example.medchannel.controller;

import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.dto.DoctorDTO;
import com.example.medchannel.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService DoctorService;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return new ResponseEntity<>(DoctorService.getAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable("id") String id) {
        return new ResponseEntity<>(DoctorService.getDoctor(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<DoctorDTO>> createDoctor(@RequestBody DoctorDTO DoctorDTO) {
        return new ResponseEntity<>(DoctorService.saveDoctor(DoctorDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<DoctorDTO>> updateDoctor(@RequestBody DoctorDTO DoctorDTO) {
        return new ResponseEntity<>(DoctorService.updateDoctor(DoctorDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<DoctorDTO>> deleteDoctor(@PathVariable("id") String id) {
        return new ResponseEntity<>(DoctorService.deleteDoctor(id), HttpStatus.OK);
    }
}