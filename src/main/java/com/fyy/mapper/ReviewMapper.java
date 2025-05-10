package com.fyy.mapper;

import com.fyy.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ReviewMapper {
    int insert(Review review);
    
    Review findById(@Param("id") Long id);
    
    List<Review> findByOrderId(@Param("orderId") Long orderId);
    
    List<Review> findByReviewerId(@Param("reviewerId") Long reviewerId);
    
    List<Review> findByReviewedId(@Param("reviewedId") Long reviewedId);
    
    List<Review> findByOrderIdAndReviewerId(@Param("orderId") Long orderId, @Param("reviewerId") Long reviewerId);
    
    Double getAverageRatingByReviewedId(@Param("reviewedId") Long reviewedId);
    
    int delete(@Param("id") Long id);
} 