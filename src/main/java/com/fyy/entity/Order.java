package com.fyy.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long productId;
    private Long buyerId;
    private Long sellerId;
    private BigDecimal price;
    private String status;
    private String address;
    private String contactName;
    private String contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 