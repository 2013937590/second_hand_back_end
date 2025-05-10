package com.fyy.service.impl;

import com.fyy.dto.ProductDTO;
import com.fyy.entity.Product;
import com.fyy.entity.ProductImage;
import com.fyy.mapper.ProductMapper;
import com.fyy.mapper.ProductImageMapper;
import com.fyy.service.ProductService;
import com.fyy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    @Transactional
    public Result<Product> createProduct(Long userId, ProductDTO productDTO) {
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setCategoryId(productDTO.getCategoryId());
        product.setPrice(productDTO.getPrice());
        product.setConditionScore(productDTO.getConditionScore());
        product.setStatus("DRAFT");
        product.setUserId(userId);
        product.setViewCount(0);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productMapper.insert(product);

        // 保存商品图片
        if (productDTO.getImages() != null && !productDTO.getImages().isEmpty()) {
            for (int i = 0; i < productDTO.getImages().size(); i++) {
                ProductImage image = new ProductImage();
                image.setProductId(product.getId());
                image.setImageUrl(productDTO.getImages().get(i));
                image.setSortOrder(i);
                image.setCreatedAt(LocalDateTime.now());
                productImageMapper.insert(image);
            }
        }

        return Result.success(product);
    }

    @Override
    public Result<Product> getProduct(Long productId) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            return Result.error("商品不存在");
        }
        return Result.success(product);
    }

    @Override
    public Result<Page<Product>> searchProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
        int offset = (page - 1) * size;
        List<Product> products = productMapper.search(keyword, categoryId, minPrice, maxPrice, offset, size);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(page, size), products.size());
        return Result.success(productPage);
    }

    @Override
    @Transactional
    public Result<Product> updateProduct(Long userId, Long productId, ProductDTO productDTO) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            return Result.error("商品不存在");
        }
        if (!product.getUserId().equals(userId)) {
            return Result.error("无权修改此商品");
        }

        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setCategoryId(productDTO.getCategoryId());
        product.setPrice(productDTO.getPrice());
        product.setConditionScore(productDTO.getConditionScore());
        product.setUpdatedAt(LocalDateTime.now());

        productMapper.update(product);

        // 更新商品图片
        if (productDTO.getImages() != null) {
            productImageMapper.deleteByProductId(productId);
            for (int i = 0; i < productDTO.getImages().size(); i++) {
                ProductImage image = new ProductImage();
                image.setProductId(productId);
                image.setImageUrl(productDTO.getImages().get(i));
                image.setSortOrder(i);
                image.setCreatedAt(LocalDateTime.now());
                productImageMapper.insert(image);
            }
        }

        return Result.success(product);
    }

    @Override
    @Transactional
    public Result<Void> deleteProduct(Long userId, Long productId) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            return Result.error("商品不存在");
        }
        if (!product.getUserId().equals(userId)) {
            return Result.error("无权删除此商品");
        }

        // 删除商品图片
        productImageMapper.deleteByProductId(productId);
        // 删除商品
        productMapper.deleteById(productId);
        
        return Result.success();
    }

    @Override
    public Result<Page<Product>> getUserProducts(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Product> products = productMapper.findByUserId(userId);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(page, size), products.size());
        return Result.success(productPage);
    }

    @Override
    public List<Product> getCategoryProducts(Long categoryId) {
        return productMapper.findByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public void updateProductStatus(Long userId, Long productId, String status) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!product.getUserId().equals(userId)) {
            throw new RuntimeException("无权修改此商品");
        }

        productMapper.updateStatus(productId, status);
    }

    @Override
    public void incrementViewCount(Long productId) {
        productMapper.incrementViewCount(productId);
    }
} 