package com.fyy.service;

import com.fyy.dto.UserDTO;
import com.fyy.entity.User;
import com.fyy.util.Result;

public interface UserService {
    Result<User> register(UserDTO userDTO);
    
    Result<String> login(String phone, String password);
    
    Result<Void> logout(String token);
    
    Result<String> refreshToken(String token);
    
    Result<User> getProfile(Long userId);
    
    Result<User> updateProfile(Long userId, UserDTO userDTO);
} 