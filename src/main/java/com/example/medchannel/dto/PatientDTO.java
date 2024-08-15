package com.example.medchannel.dto;

public record PatientDTO(String patient_id,
                         String first_name,
                         String last_name,
                         String address,
                         String phone,
                         String gender,
                         String blood_type,
                         String dob,
                         String created_at,
                         String updated_at) {

}
