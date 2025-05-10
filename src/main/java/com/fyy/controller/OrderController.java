package com.fyy.controller;

import com.fyy.dto.OrderDTO;
import com.fyy.entity.Order;
import com.fyy.service.OrderService;
import com.fyy.util.JwtUtil;
import com.fyy.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "订单管理", description = "订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "创建订单")
    public Result<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return orderService.createOrder(userId, orderDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取订单详情")
    public Result<Order> getOrder(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return orderService.getOrder(userId, id);
    }

    @GetMapping
    @Operation(summary = "获取用户订单列表")
    public Result<List<Order>> getUserOrders(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return orderService.getUserOrders(userId);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新订单状态")
    public Result<Void> updateOrderStatus(@PathVariable Long id, @RequestParam String status, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return orderService.updateOrderStatus(userId, id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "取消订单")
    public Result<Void> cancelOrder(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return orderService.cancelOrder(userId, id);
    }
} 