package com.example.medchannel.controller;

import com.example.medchannel.dto.PatientDTO;
import com.example.medchannel.entity.Patient;
import com.example.medchannel.service.PatientServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final PatientServiceImpl patientServiceImpl;

    public ImageController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("patientId") String patientID) {
        if (file.isEmpty()) {
            System.out.println("File is empty!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty!");
        }

        try {
            // Get the original filename
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());

            // Validate the file type (only allow image files)
            if (!file.getContentType().startsWith("image/")) {
                System.out.println("Invalid file type: " + file.getContentType());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Only images are allowed!");
            }

            // Upload the file
            String url = patientServiceImpl.uploadProfilePicture(file);

            // Check if the patient exists
            PatientDTO patient = patientServiceImpl.getPatient(patientID);
            if (patient == null) {
                System.out.println("Patient not found!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found!");
            } else {
                // Update the patient's image_url
                PatientDTO updatedPatient = new PatientDTO(
                        patient.patient_id(),
                        patient.first_name(),
                        patient.last_name(),
                        patient.address(),
                        patient.phone(),
                        patient.gender(),
                        patient.blood_type(),
                        patient.dob(),
                        patient.created_at(),
                        patient.updated_at(),
                        url
                );
                System.out.println("Patient: ");
                patientServiceImpl.updatePatient(updatedPatient);
                System.out.println("PatientDTO: ");
            }

            System.out.println("File uploaded successfully: " + url);
            return ResponseEntity.ok("File uploaded successfully: " + url);
        } catch (Exception e) {
            System.out.println("Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }
}


