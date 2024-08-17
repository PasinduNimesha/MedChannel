package com.example.medchannel.service;

import com.example.medchannel.dto.AppointmentDTO;
import com.example.medchannel.dto.ResponseDTO;

import java.util.List;

public interface AppointmentService {
    ResponseDTO<AppointmentDTO> saveAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointment(String id);

    ResponseDTO<AppointmentDTO> updateAppointment(AppointmentDTO appointmentDTO);

    ResponseDTO<AppointmentDTO> deleteAppointment(String id);

    List<AppointmentDTO> getAllAppointments();

    List<AppointmentDTO> getAppointmentsByPatient(String patient_id);

    List<AppointmentDTO> getAppointmentsByPhysician(String doc_id);
}
