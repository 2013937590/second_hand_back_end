package com.fyy.controller;

import com.fyy.dto.LoginDTO;
import com.fyy.dto.UserDTO;
import com.fyy.entity.User;
import com.fyy.service.UserService;
import com.fyy.util.JwtUtil;
import com.fyy.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理", description = "用户注册、登录、信息管理等接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<User> register(@Valid @RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO.getPhone(), loginDTO.getPassword());
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public Result<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.logout(token);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新token")
    public Result<String> refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.refreshToken(token);
    }

    @GetMapping("/profile")
    @Operation(summary = "获取用户信息")
    public Result<User> getProfile(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return userService.getProfile(userId);
    }

    @PutMapping("/profile")
    @Operation(summary = "更新用户信息")
    public Result<User> updateProfile(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return userService.updateProfile(userId, userDTO);
    }
} 