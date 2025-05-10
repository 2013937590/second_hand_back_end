package com.fyy.service.impl;

import com.fyy.dto.OrderDTO;
import com.fyy.entity.Order;
import com.fyy.entity.Product;
import com.fyy.mapper.OrderMapper;
import com.fyy.mapper.ProductMapper;
import com.fyy.service.OrderService;
import com.fyy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public Result<Order> createOrder(Long userId, OrderDTO orderDTO) {
        // 获取商品信息
        Product product = productMapper.findById(orderDTO.getProductId());
        if (product == null) {
            return Result.error("商品不存在");
        }
        if ("ON_SALE".equals(product.getStatus())) {
            return Result.error("商品已下架");
        }
        if ("SOLD".equals(product.getStatus())) {
            return Result.error("商品已被买走");
        }
        if (product.getUserId().equals(userId)) {
            return Result.error("不能购买自己的商品");
        }

        // 创建订单
        Order order = new Order();
        order.setProductId(product.getId());
        order.setBuyerId(userId);
        order.setSellerId(product.getUserId());
        order.setPrice(product.getPrice());
        order.setStatus("PENDING_PAYMENT");
        order.setAddress(orderDTO.getAddress());
        order.setContactName(orderDTO.getContact_name());
        order.setContactPhone(orderDTO.getContact_phone());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderMapper.insert(order);

        // 更新商品状态
        productMapper.updateStatus(product.getId(), "SOLD");

        return Result.success(order);
    }

    @Override
    public Result<Order> getOrder(Long userId, Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!order.getBuyerId().equals(userId) && !order.getSellerId().equals(userId)) {
            return Result.error("无权查看此订单");
        }
        return Result.success(order);
    }

    @Override
    public Result<List<Order>> getUserOrders(Long userId) {
        List<Order> orders = orderMapper.findByBuyerId(userId);
        return Result.success(orders);
    }

    @Override
    @Transactional
    public Result<Void> updateOrderStatus(Long userId, Long orderId, String status) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!order.getSellerId().equals(userId)) {
            return Result.error("无权修改此订单");
        }
        orderMapper.updateStatus(orderId, status);
        return Result.success();
    }

    @Override
    @Transactional
    public Result<Void> cancelOrder(Long userId, Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!order.getBuyerId().equals(userId)) {
            return Result.error("无权取消此订单");
        }
        if (!"PENDING_PAYMENT".equals(order.getStatus())) {
            return Result.error("只能取消待付款订单");
        }

        orderMapper.updateStatus(orderId, "CANCELLED");
        productMapper.updateStatus(order.getProductId(), "ON_SALE");
        return Result.success();
    }

} 