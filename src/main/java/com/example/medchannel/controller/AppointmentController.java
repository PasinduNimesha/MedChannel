package com.example.medchannel.controller;

import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.dto.AppointmentDTO;
import com.example.medchannel.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService AppointmentService;

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return new ResponseEntity<>(AppointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable("id") String id) {
        return new ResponseEntity<>(AppointmentService.getAppointment(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<AppointmentDTO>> createAppointment(@RequestBody AppointmentDTO AppointmentDTO) {
        return new ResponseEntity<>(AppointmentService.saveAppointment(AppointmentDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<AppointmentDTO>> updateAppointment(@RequestBody AppointmentDTO AppointmentDTO) {
        return new ResponseEntity<>(AppointmentService.updateAppointment(AppointmentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<AppointmentDTO>> deleteAppointment(@PathVariable("id") String id) {
        return new ResponseEntity<>(AppointmentService.deleteAppointment(id), HttpStatus.OK);
    }

    @GetMapping("/physician/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPhysician(@PathVariable("id") String id) {
        return new ResponseEntity<>(AppointmentService.getAppointmentsByPhysician(id), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(@PathVariable("id") String id) {
        return new ResponseEntity<>(AppointmentService.getAppointmentsByPatient(id), HttpStatus.OK);
    }
}