package com.example.medchannel.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "patients")
public class Patient {
    @DynamoDBHashKey(attributeName = "patient_id")
    private String patient_id;
    @DynamoDBAttribute
    private String first_name;
    @DynamoDBAttribute
    private String last_name;
    @DynamoDBAttribute
    private String address;
    @DynamoDBAttribute
    private String phone;
    @DynamoDBAttribute
    private String dob;
    @DynamoDBAttribute
    private String gender;
    @DynamoDBAttribute
    private String blood_type;
    @DynamoDBAttribute
    private String created_at;
    @DynamoDBAttribute
    private String updated_at;
}
