package com.fyy.service;

import com.fyy.dto.OrderDTO;
import com.fyy.entity.Order;
import com.fyy.util.Result;

import java.util.List;

public interface OrderService {
    Result<Order> createOrder(Long userId, OrderDTO orderDTO);
    
    Result<Order> getOrder(Long userId, Long orderId);
    
    Result<List<Order>> getUserOrders(Long userId);
    
    Result<Void> updateOrderStatus(Long userId, Long orderId, String status);
    
    Result<Void> cancelOrder(Long userId, Long orderId);

} 