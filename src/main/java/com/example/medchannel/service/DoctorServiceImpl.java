package com.example.medchannel.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.medchannel.dto.DoctorDTO;
import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.entity.Doctor;
import com.example.medchannel.exception.DoctorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ResponseDTO<DoctorDTO> saveDoctor(DoctorDTO DoctorDTO) {
        log.info("DoctorDTO => {}", DoctorDTO);
        if (ObjectUtils.isEmpty(DoctorDTO)) {
            throw new DoctorException("Doctor details cannot be null");
        }
        Doctor Doctor = new Doctor(
                DoctorDTO.doc_id(),
                DoctorDTO.id(),
                DoctorDTO.first_name(),
                DoctorDTO.last_name(),
                DoctorDTO.available(),
                DoctorDTO.experience(),
                DoctorDTO.specialty(),
                DoctorDTO.hospital(),
                DoctorDTO.license_no(),
                DoctorDTO.created_at(),
                DoctorDTO.updated_at()
        );
        dynamoDBMapper.save(Doctor);
        return new ResponseDTO<>("Doctor created successfully", new DoctorDTO(
                DoctorDTO.doc_id(),
                DoctorDTO.id(),
                Doctor.getFirst_name(),
                Doctor.getLast_name(),
                Doctor.isAvailable(),
                Doctor.getExperience(),
                Doctor.getSpecialty(),
                Doctor.getHospital(),
                Doctor.getLicense_no(),
                Doctor.getCreated_at(),
                Doctor.getUpdated_at()
        ));
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> DoctorList = dynamoDBMapper.scan(Doctor.class, new DynamoDBScanExpression());
        log.info("Doctor list count => {}", DoctorList.size());
        return DoctorList.stream().map(e -> new DoctorDTO(
                e.getDoc_id(),
                e.getId(),
                e.getFirst_name(),
                e.getLast_name(),
                e.isAvailable(),
                e.getExperience(),
                e.getSpecialty(),
                e.getHospital(),
                e.getLicense_no(),
                e.getCreated_at(),
                e.getUpdated_at()
        )).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO getDoctor(String id) {
        log.info("Doctor id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new DoctorException("Doctor id cannot be null");
        }
        Doctor Doctor = dynamoDBMapper.load(Doctor.class, id);
        return new DoctorDTO(
                Doctor.getDoc_id(),
                Doctor.getId(),
                Doctor.getFirst_name(),
                Doctor.getLast_name(),
                Doctor.isAvailable(),
                Doctor.getExperience(),
                Doctor.getSpecialty(),
                Doctor.getHospital(),
                Doctor.getLicense_no(),
                Doctor.getCreated_at(),
                Doctor.getUpdated_at()
        );
    }

    @Override
    public ResponseDTO<DoctorDTO> updateDoctor(DoctorDTO DoctorDTO) {
        log.info("DoctorDTO => {}", DoctorDTO);
        if (ObjectUtils.isEmpty(DoctorDTO)) {
            throw new DoctorException("Doctor details cannot be null");
        }
        Doctor Doctor = new Doctor(
                DoctorDTO.doc_id(),
                DoctorDTO.id(),
                DoctorDTO.first_name(),
                DoctorDTO.last_name(),
                DoctorDTO.available(),
                DoctorDTO.experience(),
                DoctorDTO.specialty(),
                DoctorDTO.hospital(),
                DoctorDTO.license_no(),
                DoctorDTO.created_at(),
                DoctorDTO.updated_at()
        );
        dynamoDBMapper.save(Doctor, buildExpression(Doctor));
        return new ResponseDTO<>("Doctor updated successfully", new DoctorDTO(
                Doctor.getDoc_id(),
                Doctor.getId(),
                Doctor.getFirst_name(),
                Doctor.getLast_name(),
                Doctor.isAvailable(),
                Doctor.getExperience(),
                Doctor.getSpecialty(),
                Doctor.getHospital(),
                Doctor.getLicense_no(),
                Doctor.getCreated_at(),
                Doctor.getUpdated_at()
                ));
    }

    @Override
    public ResponseDTO<DoctorDTO> deleteDoctor(String id) {
        log.info("Doctor id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new DoctorException("Doctor id cannot be null");
        }
        Doctor Doctor = dynamoDBMapper.load(Doctor.class, id);
        if (ObjectUtils.isEmpty(Doctor)) {
            throw new DoctorException("No data found");
        }
        dynamoDBMapper.delete(Doctor);
        return new ResponseDTO<>("Doctor deleted successfully", null);
    }

    private DynamoDBSaveExpression buildExpression(Doctor Doctor) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(Doctor.getId())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }
}
