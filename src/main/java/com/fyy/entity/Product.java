package com.fyy.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private BigDecimal price;
    private Integer conditionScore;
    private String status;
    private Long userId;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 