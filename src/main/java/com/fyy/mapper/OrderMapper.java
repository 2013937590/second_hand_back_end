package com.fyy.mapper;

import com.fyy.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Order order);
    
    Order findById(@Param("id") Long id);

    List<Order> findByBuyerId(@Param("buyerId") Long buyerId);
    
    List<Order> findBySellerId(@Param("sellerId") Long sellerId);
    
    int updateStatus(@Param("id") Long id, @Param("status") String status);
} 