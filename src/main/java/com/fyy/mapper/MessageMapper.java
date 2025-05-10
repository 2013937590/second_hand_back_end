package com.fyy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyy.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    int insert(Message message);
    
    Message findById(@Param("id") Long id);
    
    List<Message> findByUserId(@Param("userId") Long userId);
    
    List<Message> findBySenderId(@Param("senderId") Long senderId);
    
    List<Message> findByReceiverId(@Param("receiverId") Long receiverId);
    
    List<Message> findUnreadByReceiverId(@Param("receiverId") Long receiverId);
    
    int markAsRead(@Param("id") Long id);
    
    int markAllAsRead(@Param("receiverId") Long receiverId);
    
    int delete(@Param("id") Long id);
} 