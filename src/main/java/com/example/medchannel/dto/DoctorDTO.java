package com.example.medchannel.dto;

public record DoctorDTO(String doc_id,
                     String id,
                     boolean available,
                     int experience,
                     String specialty,
                     String hospital,
                     String license_no,
                     String created_at,
                     String updated_at) {
}
