package com.fyy.controller;

import com.fyy.dto.ProductDTO;
import com.fyy.entity.Product;
import com.fyy.service.ProductService;
import com.fyy.util.JwtUtil;
import com.fyy.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "商品管理", description = "商品相关接口")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "发布商品")
    public Result<Product> createProduct(@Valid @RequestBody ProductDTO productDTO, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return productService.createProduct(userId, productDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商品详情")
    public Result<Product> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/search")
    @Operation(summary = "搜索商品")
    public Result<Page<Product>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.searchProducts(keyword, categoryId, minPrice, maxPrice, page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品信息")
    public Result<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return productService.updateProduct(userId, id, productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品")
    public Result<Void> deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return productService.deleteProduct(userId, id);
    }

    @GetMapping("/user")
    @Operation(summary = "获取用户发布的商品")
    public Result<Page<Product>> getUserProducts(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return productService.getUserProducts(userId, page, size);
    }
} 