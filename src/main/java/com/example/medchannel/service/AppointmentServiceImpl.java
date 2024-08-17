package com.example.medchannel.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.medchannel.dto.AppointmentDTO;
import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.entity.Appointment;
import com.example.medchannel.exception.AppointmentException;
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
public class AppointmentServiceImpl implements AppointmentService {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ResponseDTO<AppointmentDTO> saveAppointment(AppointmentDTO AppointmentDTO) {
        log.info("AppointmentDTO => {}", AppointmentDTO);
        if (ObjectUtils.isEmpty(AppointmentDTO)) {
            throw new AppointmentException("Appointment details cannot be null");
        }
        Appointment Appointment = new Appointment(
                AppointmentDTO.appointment_id(),
                AppointmentDTO.patient_id(),
                AppointmentDTO.doc_id(),
                AppointmentDTO.appointment_date(),
                AppointmentDTO.appointment_time(),
                AppointmentDTO.created_at(),
                AppointmentDTO.updated_at()

        );
        dynamoDBMapper.save(Appointment);
        return new ResponseDTO<>("Appointment created successfully", new AppointmentDTO(
                AppointmentDTO.appointment_id(),
                AppointmentDTO.patient_id(),
                AppointmentDTO.doc_id(),
                AppointmentDTO.appointment_date(),
                AppointmentDTO.appointment_time(),
                AppointmentDTO.created_at(),
                AppointmentDTO.updated_at()

        ));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> AppointmentList = dynamoDBMapper.scan(Appointment.class, new DynamoDBScanExpression());
        log.info("Appointment list count => {}", AppointmentList.size());
        return AppointmentList.stream().map(e -> new AppointmentDTO(
                e.getAppointment_id(),
                e.getPatient_id(),
                e.getDoc_id(),
                e.getAppointment_date(),
                e.getAppointment_time(),
                e.getCreated_at(),
                e.getUpdated_at()

        )).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatient(String patient_id) {
        if (!StringUtils.hasLength(patient_id)) {
            throw new AppointmentException("Patient id cannot be null");
        }

        // Create a query expression to filter by patient_id
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":patient_id", new AttributeValue().withS(patient_id));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("patient_id = :patient_id")
                .withExpressionAttributeValues(eav);

        List<Appointment> appointmentList = dynamoDBMapper.scan(Appointment.class, scanExpression);
        log.info("Appointment list count for patient {} => {}", patient_id, appointmentList.size());

        return appointmentList.stream().map(e -> new AppointmentDTO(
                e.getAppointment_id(),
                e.getPatient_id(),
                e.getDoc_id(),
                e.getAppointment_date(),
                e.getAppointment_time(),
                e.getCreated_at(),
                e.getUpdated_at()
        )).collect(Collectors.toList());
    }


    @Override
    public List<AppointmentDTO> getAppointmentsByPhysician(String doc_id) {
        if (!StringUtils.hasLength(doc_id)) {
            throw new AppointmentException("Doctor id cannot be null");
        }

        // Create a query expression to filter by doc_id
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":doc_id", new AttributeValue().withS(doc_id));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("doc_id = :doc_id")
                .withExpressionAttributeValues(eav);

        List<Appointment> appointmentList = dynamoDBMapper.scan(Appointment.class, scanExpression);
        log.info("Appointment list count for physician {} => {}", doc_id, appointmentList.size());

        return appointmentList.stream().map(e -> new AppointmentDTO(
                e.getAppointment_id(),
                e.getPatient_id(),
                e.getDoc_id(),
                e.getAppointment_date(),
                e.getAppointment_time(),
                e.getCreated_at(),
                e.getUpdated_at()
        )).collect(Collectors.toList());
    }


    @Override
    public AppointmentDTO getAppointment(String id) {
        log.info("Appointment id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new AppointmentException("Appointment id cannot be null");
        }
        Appointment Appointment = dynamoDBMapper.load(Appointment.class, id);
        return new AppointmentDTO(
                Appointment.getAppointment_id(),
                Appointment.getPatient_id(),
                Appointment.getDoc_id(),
                Appointment.getAppointment_date(),
                Appointment.getAppointment_time(),
                Appointment.getCreated_at(),
                Appointment.getUpdated_at()

        );
    }

    @Override
    public ResponseDTO<AppointmentDTO> updateAppointment(AppointmentDTO AppointmentDTO) {
        log.info("AppointmentDTO => {}", AppointmentDTO);
        if (ObjectUtils.isEmpty(AppointmentDTO)) {
            throw new AppointmentException("Appointment details cannot be null");
        }
        Appointment Appointment = new Appointment(
                AppointmentDTO.appointment_id(),
                AppointmentDTO.patient_id(),
                AppointmentDTO.doc_id(),
                AppointmentDTO.appointment_date(),
                AppointmentDTO.appointment_time(),
                AppointmentDTO.created_at(),
                AppointmentDTO.updated_at()

        );
        dynamoDBMapper.save(Appointment, buildExpression(Appointment));
        return new ResponseDTO<>("Appointment updated successfully", new AppointmentDTO(
                AppointmentDTO.appointment_id(),
                AppointmentDTO.patient_id(),
                AppointmentDTO.doc_id(),
                AppointmentDTO.appointment_date(),
                AppointmentDTO.appointment_time(),
                AppointmentDTO.created_at(),
                AppointmentDTO.updated_at()

        ));
    }

    @Override
    public ResponseDTO<AppointmentDTO> deleteAppointment(String id) {
        log.info("Appointment id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new AppointmentException("Appointment id cannot be null");
        }
        Appointment Appointment = dynamoDBMapper.load(Appointment.class, id);
        if (ObjectUtils.isEmpty(Appointment)) {
            throw new AppointmentException("No data found");
        }
        dynamoDBMapper.delete(Appointment);
        return new ResponseDTO<>("Appointment deleted successfully", null);
    }

    private DynamoDBSaveExpression buildExpression(Appointment Appointment) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(Appointment.getAppointment_id())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }
}