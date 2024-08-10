package com.example.medchannel.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.medchannel.dto.UserDTO;
import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.entity.User;
import com.example.medchannel.exception.UserException;
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
public class UserServiceImpl implements UserService {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ResponseDTO<UserDTO> saveUser(UserDTO userDTO) {
        log.info("userDTO => {}", userDTO);
        if (ObjectUtils.isEmpty(userDTO)) {
            throw new UserException("User details cannot be null");
        }
        User user = new User(userDTO.id(), userDTO.username(), userDTO.email(), userDTO.password_hash());
        dynamoDBMapper.save(user);
        return new ResponseDTO<>("User created successfully", new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword_hash()));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        log.info("User list count => {}", userList.size());
        return userList.stream().map(e -> new UserDTO(e.getId(), e.getUsername(), e.getEmail(), e.getPassword_hash())).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(String id) {
        log.info("User id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new UserException("User id cannot be null");
        }
        User user = dynamoDBMapper.load(User.class, id);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword_hash());
    }

    @Override
    public ResponseDTO<UserDTO> updateUser(UserDTO userDTO) {
        log.info("userDTO => {}", userDTO);
        if (ObjectUtils.isEmpty(userDTO)) {
            throw new UserException("User details cannot be null");
        }
        User user = new User(userDTO.id(), userDTO.username(), userDTO.email(), userDTO.password_hash());
        dynamoDBMapper.save(user, buildExpression(user));
        return new ResponseDTO<>("User updated successfully", new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword_hash()));
    }

    @Override
    public ResponseDTO<UserDTO> deleteUser(String id) {
        log.info("User id => {}", id);
        if (!StringUtils.hasLength(id)) {
            throw new UserException("User id cannot be null");
        }
        User user = dynamoDBMapper.load(User.class, id);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserException("No data found");
        }
        dynamoDBMapper.delete(user);
        return new ResponseDTO<>("User deleted successfully", null);
    }

    private DynamoDBSaveExpression buildExpression(User user) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(user.getId())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }
}
