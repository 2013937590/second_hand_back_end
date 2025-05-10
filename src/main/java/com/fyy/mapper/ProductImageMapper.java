package com.fyy.mapper;

import com.fyy.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductImageMapper {
    int insert(ProductImage productImage);
    
    List<ProductImage> findByProductId(@Param("productId") Long productId);
    
    int deleteByProductId(@Param("productId") Long productId);
} 