package com.fyy.service;

import com.fyy.dto.ReviewDTO;
import com.fyy.entity.Review;
import com.fyy.util.Result;

import java.util.List;

public interface ReviewService {
    Result<Review> createReview(Long userId, ReviewDTO reviewDTO);
    
    Result<Review> getReview(Long id);
    
    Result<List<Review>> getUserReviews(Long userId);
    
    Result<Void> deleteReview(Long userId, Long reviewId);
}