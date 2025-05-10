package com.fyy.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductImage {
    private Long id;
    private Long productId;
    private String imageUrl;
    private Integer sortOrder;
    private LocalDateTime createdAt;
} 