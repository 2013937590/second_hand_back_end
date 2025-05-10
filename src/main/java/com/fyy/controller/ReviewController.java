package com.fyy.controller;

import com.fyy.dto.ReviewDTO;
import com.fyy.entity.Review;
import com.fyy.service.ReviewService;
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
@RequestMapping("/api/v1/reviews")
@Tag(name = "评价管理", description = "商品评价相关接口")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "创建评价")
    public Result<Review> createReview(@Valid @RequestBody ReviewDTO reviewDTO, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return reviewService.createReview(userId, reviewDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取评价详情")
    public Result<Review> getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评价")
    public Result<Void> deleteReview(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return reviewService.deleteReview(userId, id);
    }
} 