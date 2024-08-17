package com.example.medchannel.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.medchannel.dto.PhysicianDTO;
import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.entity.Physician;
import com.example.medchannel.exception.PhysicianException;
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
public class PhysicianServiceImpl implements PhysicianService {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ResponseDTO<PhysicianDTO> savePhysician(PhysicianDTO PhysicianDTO) {
        log.info("PhysicianDTO => {}", PhysicianDTO);
        if (ObjectUtils.isEmpty(PhysicianDTO)) {
            throw new PhysicianException("Physician details cannot be null");
        }
        Physician Physician = new Physician(
                PhysicianDTO.doc_id(),
                PhysicianDTO.first_name(),
                PhysicianDTO.last_name(),
                PhysicianDTO.available(),
                PhysicianDTO.experience(),
                PhysicianDTO.specialty(),
                PhysicianDTO.hospital(),
                PhysicianDTO.license_no(),
                PhysicianDTO.created_at(),
                PhysicianDTO.updated_at()
        );
        dynamoDBMapper.save(Physician);
        return new ResponseDTO<>("Physician created successfully", new PhysicianDTO(
                PhysicianDTO.doc_id(),
                Physician.getFirst_name(),
                Physician.getLast_name(),
                Physician.isAvailable(),
                Physician.getExperience(),
                Physician.getSpecialty(),
                Physician.getHospital(),
                Physician.getLicense_no(),
                Physician.getCreated_at(),
                Physician.getUpdated_at()
        ));
    }

    @Override
    public List<PhysicianDTO> getAllPhysicians() {
        List<Physician> PhysicianList = dynamoDBMapper.scan(Physician.class, new DynamoDBScanExpression());
        log.info("Physician list count => {}", PhysicianList.size());
        return PhysicianList.stream().map(e -> new PhysicianDTO(
                e.getDoc_id(),
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
    public PhysicianDTO getPhysician(String id) {
        log.info("Physician id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new PhysicianException("Physician id cannot be null");
        }
        Physician Physician = dynamoDBMapper.load(Physician.class, id);
        return new PhysicianDTO(
                Physician.getDoc_id(),
                Physician.getFirst_name(),
                Physician.getLast_name(),
                Physician.isAvailable(),
                Physician.getExperience(),
                Physician.getSpecialty(),
                Physician.getHospital(),
                Physician.getLicense_no(),
                Physician.getCreated_at(),
                Physician.getUpdated_at()
        );
    }

    @Override
    public ResponseDTO<PhysicianDTO> updatePhysician(PhysicianDTO PhysicianDTO) {
        log.info("PhysicianDTO => {}", PhysicianDTO);
        if (ObjectUtils.isEmpty(PhysicianDTO)) {
            throw new PhysicianException("Physician details cannot be null");
        }
        Physician Physician = new Physician(
                PhysicianDTO.doc_id(),
                PhysicianDTO.first_name(),
                PhysicianDTO.last_name(),
                PhysicianDTO.available(),
                PhysicianDTO.experience(),
                PhysicianDTO.specialty(),
                PhysicianDTO.hospital(),
                PhysicianDTO.license_no(),
                PhysicianDTO.created_at(),
                PhysicianDTO.updated_at()
        );
        dynamoDBMapper.save(Physician, buildExpression(Physician));
        return new ResponseDTO<>("Physician updated successfully", new PhysicianDTO(
                Physician.getDoc_id(),
                Physician.getFirst_name(),
                Physician.getLast_name(),
                Physician.isAvailable(),
                Physician.getExperience(),
                Physician.getSpecialty(),
                Physician.getHospital(),
                Physician.getLicense_no(),
                Physician.getCreated_at(),
                Physician.getUpdated_at()
                ));
    }

    @Override
    public ResponseDTO<PhysicianDTO> deletePhysician(String id) {
        log.info("Physician id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new PhysicianException("Physician id cannot be null");
        }
        Physician Physician = dynamoDBMapper.load(Physician.class, id);
        if (ObjectUtils.isEmpty(Physician)) {
            throw new PhysicianException("No data found");
        }
        dynamoDBMapper.delete(Physician);
        return new ResponseDTO<>("Physician deleted successfully", null);
    }

    private DynamoDBSaveExpression buildExpression(Physician Physician) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(Physician.getDoc_id())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }
}
