package com.fyy.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long orderId;
    private Long reviewerId;
    private Long reviewedId;
    private Integer rating;
    private String comment;
    private Boolean isAnonymous;
    private LocalDateTime createdAt;
} 