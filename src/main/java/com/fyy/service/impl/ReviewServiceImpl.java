package com.fyy.service.impl;

import com.fyy.dto.ReviewDTO;
import com.fyy.entity.Order;
import com.fyy.entity.Review;
import com.fyy.mapper.OrderMapper;
import com.fyy.mapper.ReviewMapper;
import com.fyy.service.ReviewService;
import com.fyy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public Result<Review> createReview(Long userId, ReviewDTO reviewDTO) {
        // 获取订单信息
        Order order = orderMapper.findById(reviewDTO.getOrderId());
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!"COMPLETED".equals(order.getStatus())) {
            return Result.error("只能评价已完成的订单");
        }

        // 检查是否已经评价过
        List<Review> existingReviews = reviewMapper.findByOrderIdAndReviewerId(order.getId(), userId);
        if (!existingReviews.isEmpty()) {
            return Result.error("已经评价过了");
        }

        // 创建评价
        Review review = new Review();
        review.setOrderId(order.getId());
        review.setReviewerId(userId);
        review.setReviewedId(order.getBuyerId().equals(userId) ? order.getSellerId() : order.getBuyerId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setIsAnonymous(reviewDTO.getIsAnonymous());
        review.setCreatedAt(LocalDateTime.now());

        reviewMapper.insert(review);

        return Result.success(review);
    }

    @Override
    public Result<Review> getReview(Long reviewId) {
        Review review = reviewMapper.findById(reviewId);
        if (review == null) {
            return Result.error("评价不存在");
        }
        return Result.success(review);
    }

    @Override
    public Result<List<Review>> getUserReviews(Long userId) {
        List<Review> reviews = reviewMapper.findByReviewedId(userId);
        return Result.success(reviews);
    }

    @Override
    @Transactional
    public Result<Void> deleteReview(Long userId, Long reviewId) {
        Review review = reviewMapper.findById(reviewId);
        if (review == null) {
            return Result.error("评价不存在");
        }
        if (!review.getReviewerId().equals(userId)) {
            return Result.error("无权删除此评价");
        }

        reviewMapper.delete(reviewId);
        return Result.success();
    }
} 