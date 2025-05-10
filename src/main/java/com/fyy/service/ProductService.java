package com.fyy.service;

import com.fyy.dto.ProductDTO;
import com.fyy.entity.Product;
import com.fyy.util.Result;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ProductService {
    Result<Product> createProduct(Long userId, ProductDTO productDTO);
    
    Result<Product> getProduct(Long id);
    
    Result<Page<Product>> searchProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size);
    
    Result<Product> updateProduct(Long userId, Long id, ProductDTO productDTO);
    
    Result<Void> deleteProduct(Long userId, Long id);
    
    Result<Page<Product>> getUserProducts(Long userId, int page, int size);
    
    List<Product> getCategoryProducts(Long categoryId);
    
    void updateProductStatus(Long userId, Long productId, String status);
    
    void incrementViewCount(Long productId);
} 