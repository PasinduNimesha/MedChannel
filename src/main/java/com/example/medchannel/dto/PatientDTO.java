package com.example.medchannel.dto;

public record PatientDTO(String patient_id,
                         String id,
                         String address,
                         String phone,
                         String gender,
                         String blood_type,
                         String dob,
                         String created_at,
                         String updated_at) {

}
