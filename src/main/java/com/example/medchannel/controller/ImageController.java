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
import java.util.Objects;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final PatientServiceImpl patientServiceImpl;

    public ImageController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("patientID") String patientID) {
        // Get patientID from json body

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
            //upload the file
            String url = patientServiceImpl.uploadProfilePicture(file);

            //check if the patient exists
            Patient patient = new ModelMapper().map(patientServiceImpl.getPatient(patientID), Patient.class);
            if (patient == null) {
                System.out.println("Patient not found!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found!");
            } else {
                //update the patient's image_url
                patient.setImage_url(url);
            }

            System.out.println("File uploaded successfully: " + url);

            System.out.println("File uploaded successfully: ");
            return ResponseEntity.ok("File uploaded successfully: ");
        } catch (Exception e) {
            System.out.println("Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }
}


