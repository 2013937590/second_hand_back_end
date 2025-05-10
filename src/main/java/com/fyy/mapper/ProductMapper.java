package com.fyy.mapper;

import com.fyy.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {
    int insert(Product product);
    
    Product findById(@Param("id") Long id);
    
    List<Product> findByUserId(@Param("userId") Long userId);
    
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    int update(Product product);
    
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    int incrementViewCount(@Param("id") Long id);
    
    int deleteById(@Param("id") Long id);
    
    List<Product> search(@Param("keyword") String keyword, 
                        @Param("categoryId") Long categoryId,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("offset") Integer offset,
                        @Param("limit") Integer limit);
} 