package com.example.medchannel.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.medchannel.dto.PatientDTO;
import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.entity.Patient;
import com.example.medchannel.exception.PatientException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ResponseDTO<PatientDTO> savePatient(PatientDTO PatientDTO) {
        log.info("PatientDTO => {}", PatientDTO);
        if (ObjectUtils.isEmpty(PatientDTO)) {
            throw new PatientException("Patient details cannot be null");
        }
        Patient Patient = new Patient(
                PatientDTO.patient_id(),
                PatientDTO.first_name(),
                PatientDTO.last_name(),
                PatientDTO.address(),
                PatientDTO.phone(),
                PatientDTO.dob(),
                PatientDTO.gender(),
                PatientDTO.blood_type(),
                PatientDTO.created_at(),
                PatientDTO.updated_at(),
                "test100"
                );
        dynamoDBMapper.save(Patient);
        return new ResponseDTO<>("Patient created successfully", new PatientDTO(
                PatientDTO.patient_id(),
                PatientDTO.first_name(),
                PatientDTO.last_name(),
                PatientDTO.address(),
                PatientDTO.phone(),
                PatientDTO.dob(),
                PatientDTO.gender(),
                PatientDTO.blood_type(),
                PatientDTO.created_at(),
                PatientDTO.updated_at(),
                PatientDTO.image_url()
        ));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> PatientList = dynamoDBMapper.scan(Patient.class, new DynamoDBScanExpression());
        log.info("Patient list count => {}", PatientList.size());
        return PatientList.stream().map(e -> new PatientDTO(
                e.getPatient_id(),
                e.getFirst_name(),
                e.getLast_name(),
                e.getAddress(),
                e.getPhone(),
                e.getDob(),
                e.getGender(),
                e.getBlood_type(),
                e.getCreated_at(),
                e.getUpdated_at(),
                e.getImage_url()
                )).collect(Collectors.toList());
    }

    @Override
    public PatientDTO getPatient(String id) {
        log.info("Patient id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new PatientException("Patient id cannot be null");
        }
        Patient Patient = dynamoDBMapper.load(Patient.class, id);
        return new PatientDTO(
                Patient.getPatient_id(),
                Patient.getFirst_name(),
                Patient.getLast_name(),
                Patient.getAddress(),
                Patient.getPhone(),
                Patient.getDob(),
                Patient.getGender(),
                Patient.getBlood_type(),
                Patient.getCreated_at(),
                Patient.getUpdated_at(),
                Patient.getImage_url()
                );
    }

    @Override
    public ResponseDTO<PatientDTO> updatePatient(PatientDTO PatientDTO) {
        log.info("PatientDTO => {}", PatientDTO);
        if (ObjectUtils.isEmpty(PatientDTO)) {
            throw new PatientException("Patient details cannot be null");
        }
        Patient Patient = new Patient(
                PatientDTO.patient_id(),
                PatientDTO.first_name(),
                PatientDTO.last_name(),
                PatientDTO.address(),
                PatientDTO.phone(),
                PatientDTO.dob(),
                PatientDTO.gender(),
                PatientDTO.blood_type(),
                PatientDTO.created_at(),
                PatientDTO.updated_at(),
                PatientDTO.image_url()
                );
        dynamoDBMapper.save(Patient, buildExpression(Patient));
        return new ResponseDTO<>("Patient updated successfully", new PatientDTO(
                PatientDTO.patient_id(),
                PatientDTO.first_name(),
                PatientDTO.last_name(),
                PatientDTO.address(),
                PatientDTO.phone(),
                PatientDTO.dob(),
                PatientDTO.gender(),
                PatientDTO.blood_type(),
                PatientDTO.created_at(),
                PatientDTO.updated_at(),
                PatientDTO.image_url()
        ));
    }

    @Override
    public ResponseDTO<PatientDTO> deletePatient(String id) {
        log.info("Patient id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new PatientException("Patient id cannot be null");
        }
        Patient Patient = dynamoDBMapper.load(Patient.class, id);
        if (ObjectUtils.isEmpty(Patient)) {
            throw new PatientException("No data found");
        }
        dynamoDBMapper.delete(Patient);
        return new ResponseDTO<>("Patient deleted successfully", null);
    }

    private DynamoDBSaveExpression buildExpression(Patient Patient) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("patient_id", new ExpectedAttributeValue(new AttributeValue().withS(Patient.getPatient_id())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }

    private final AmazonS3 amazonS3;


    public String uploadProfilePicture(MultipartFile file) throws IOException {
        String fileName = "profile-pictures/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        // Add metadata for the file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // Upload to S3
        String bucketName = "medchannel.profilepics";
        amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);

        // Return the file URL
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
}