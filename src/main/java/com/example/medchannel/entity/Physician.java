package com.example.medchannel.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "doctors")
public class Physician{
    @DynamoDBHashKey(attributeName = "doc_id")
    private String doc_id;
    @DynamoDBAttribute
    private String first_name;
    @DynamoDBAttribute
    private String last_name;
    @DynamoDBAttribute
    private boolean available;
    @DynamoDBAttribute
    private int experience;
    @DynamoDBAttribute
    private String specialty;
    @DynamoDBAttribute
    private String hospital;
    @DynamoDBAttribute
    private String license_no;
    @DynamoDBAttribute
    private String created_at;
    @DynamoDBAttribute
    private String updated_at;
}
