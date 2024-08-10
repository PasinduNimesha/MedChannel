package com.example.medchannel.service;

import com.example.medchannel.dto.ResponseDTO;
import com.example.medchannel.dto.UserDTO;

import java.util.List;

public interface UserService {
    ResponseDTO<UserDTO> saveUser(UserDTO employeeDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUser(String id);

    ResponseDTO<UserDTO> updateUser(UserDTO userDTO);

    ResponseDTO<UserDTO> deleteUser(String id);
}
