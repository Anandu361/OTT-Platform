package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.UserDto;
import com.example.backend.models.UserModel;
import com.example.backend.repository.UserRepository;

@Service
public class UserService{
   
    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Autowired
    private UserRepository userRepository;

    public UserModel save(UserDto userDto) {
        UserModel user = new UserModel(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getFullname());
        return userRepository.save(user);
    }
}