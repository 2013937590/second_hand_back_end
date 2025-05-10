package com.fyy.mapper;

import com.fyy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByPhone(@Param("phone") String phone);
    
    int insert(User user);
    
    User findById(@Param("id") Long id);
    
    int update(User user);
} 