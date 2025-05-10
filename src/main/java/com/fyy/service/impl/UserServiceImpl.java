package com.fyy.service.impl;

import com.fyy.dto.UserDTO;
import com.fyy.entity.User;
import com.fyy.mapper.UserMapper;
import com.fyy.service.UserService;
import com.fyy.util.JwtUtil;
import com.fyy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public Result<User> register(UserDTO userDTO) {
        try {
            // 检查手机号是否已注册
            if (userMapper.findByPhone(userDTO.getPhone()) != null) {
                return Result.error("手机号已注册");
            }

            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
            user.setPhone(userDTO.getPhone());
            user.setAvatarUrl(userDTO.getAvatarUrl());
            user.setBalance(BigDecimal.ZERO);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            userMapper.insert(user);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    @Override
    public Result<String> login(String phone, String password) {
        try {
            Long id = userMapper.findByPhone(phone).getId();
            // 验证用户名和密码
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password)
            );

            // 获取用户ID并生成token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(Long.parseLong(userDetails.getUsername()));
            return Result.success(token);
        } catch (Exception e) {
            return Result.error("手机号或密码错误");
        }
    }

    @Override
    public Result<Void> logout(String token) {
        try {
            // 将token加入黑名单
            jwtUtil.addToBlacklist(token);
            return Result.success();
        } catch (Exception e) {
            return Result.error("登出失败：" + e.getMessage());
        }
    }

    @Override
    public Result<String> refreshToken(String token) {
        try {
            // 验证旧token
            if (!jwtUtil.validateToken(token)) {
                return Result.error("无效的token");
            }

            // 生成新token
            Long userId = jwtUtil.getUserIdFromToken(token);
            String newToken = jwtUtil.generateToken(userId);
            return Result.success(newToken);
        } catch (Exception e) {
            return Result.error("刷新token失败：" + e.getMessage());
        }
    }

    @Override
    public Result<User> getProfile(Long userId) {
        try {
            User user = userMapper.findById(userId);
            user.setPasswordHash(null);
            if (user == null) {
                return Result.error("用户不存在");
            }
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<User> updateProfile(Long userId, UserDTO userDTO) {
        try {
            User user = userMapper.findById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }

            user.setUsername(userDTO.getUsername());
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
            }
            user.setAvatarUrl(userDTO.getAvatarUrl());
            user.setUpdatedAt(LocalDateTime.now());

            userMapper.update(user);
            user.setPasswordHash(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("更新用户信息失败：" + e.getMessage());
        }
    }
} 