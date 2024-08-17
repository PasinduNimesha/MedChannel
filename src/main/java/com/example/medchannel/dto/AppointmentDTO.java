package com.example.medchannel.dto;

public record AppointmentDTO(String appointment_id,
                             String patient_id,
                             String doc_id,
                             String appointment_date,
                             String appointment_time,
                             String created_at,
                             String updated_at) {
}
