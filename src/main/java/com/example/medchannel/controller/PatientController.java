package com.example.medchannel.controller;

import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.dto.PatientDTO;
import com.example.medchannel.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService PatientService;

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return new ResponseEntity<>(PatientService.getAllPatients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable("id") String id) {
        return new ResponseEntity<>(PatientService.getPatient(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PatientDTO>> createPatient(@RequestBody PatientDTO PatientDTO) {
        return new ResponseEntity<>(PatientService.savePatient(PatientDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<PatientDTO>> updatePatient(@RequestBody PatientDTO PatientDTO) {
        return new ResponseEntity<>(PatientService.updatePatient(PatientDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<PatientDTO>> deletePatient(@PathVariable("id") String id) {
        return new ResponseEntity<>(PatientService.deletePatient(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<PatientDTO>> createPatient(
            @RequestPart("data") PatientDTO patientDTO,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) throws IOException {

        String profilePictureUrl = null;
        if (profilePicture != null && !profilePicture.isEmpty()) {
            // Upload to S3 and get the URL
            System.out.println("Uploaded profile picture to S3");
        }

        // Set the profile picture URL in the DTO
//        patientDTO.setProfilePictureUrl(profilePictureUrl);

        return new ResponseEntity<>(PatientService.savePatient(patientDTO), HttpStatus.CREATED);
    }
}