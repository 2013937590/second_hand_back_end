package com.fyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fyy.dto.MessageDTO;
import com.fyy.entity.Message;
import com.fyy.mapper.MessageMapper;
import com.fyy.service.MessageService;
import com.fyy.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional
    public Result<Message> sendMessage(Long userId, MessageDTO messageDTO) {
        Message message = new Message();
        BeanUtils.copyProperties(messageDTO, message);
        message.setSenderId(userId);
        message.setIsRead(false);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        messageMapper.insert(message);
        return Result.success(message);
    }

    @Override
    public Result<Message> getMessage(Long id) {
        Message message = messageMapper.findById(id);
        if (message == null) {
            return Result.error("消息不存在");
        }
        return Result.success(message);
    }

    @Override
    public Result<List<Message>> getConversationMessages(Long userId, Long conversationId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.nested(wq -> wq
                        .eq(Message::getSenderId, userId)
                        .eq(Message::getReceiverId, conversationId)
                )
                .or(wq -> wq
                        .eq(Message::getSenderId, conversationId)
                        .eq(Message::getReceiverId, userId)
                )
                .orderByAsc(Message::getCreatedAt);

        List<Message> messages = messageMapper.selectList(wrapper);
        return Result.success(messages);
    }

    @Override
    @Transactional
    public Result<Void> markMessageAsRead(Long userId, Long messageId) {
        Message message = messageMapper.selectById(messageId);
        if (message == null) {
            return Result.error("消息不存在");
        }
        if (!message.getReceiverId().equals(userId)) {
            return Result.error("无权操作此消息");
        }
        message.setIsRead(true);
        message.setUpdatedAt(LocalDateTime.now());
        messageMapper.updateById(message);
        return Result.success();
    }

    @Override
    @Transactional
    public Result<Void> deleteMessage(Long userId, Long messageId) {
        Message message = messageMapper.selectById(messageId);
        if (message == null) {
            return Result.error("消息不存在");
        }
        if (!message.getSenderId().equals(userId) && !message.getReceiverId().equals(userId)) {
            return Result.error("无权删除此消息");
        }
        messageMapper.deleteById(messageId);
        return Result.success();
    }
} 