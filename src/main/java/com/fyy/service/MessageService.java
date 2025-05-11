package com.fyy.service;

import com.fyy.dto.MessageDTO;
import com.fyy.entity.Message;
import com.fyy.util.Result;

import java.util.List;

public interface MessageService {
    Result<Message> sendMessage(Long userId, MessageDTO messageDTO);
    
    Result<Message> getMessage(Long id);
    
    Result<List<Message>> getConversationMessages(Long userId, Long conversationId);

    Result<List<Message>> getAllConversationMessages(Long userId);
    
    Result<Void> markMessageAsRead(Long userId, Long messageId);
    
    Result<Void> deleteMessage(Long userId, Long messageId);
} 