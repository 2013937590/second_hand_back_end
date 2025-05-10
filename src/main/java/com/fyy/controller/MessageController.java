package com.fyy.controller;

import com.fyy.dto.MessageDTO;
import com.fyy.entity.Message;
import com.fyy.service.MessageService;
import com.fyy.util.JwtUtil;
import com.fyy.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@Tag(name = "消息管理", description = "消息相关接口")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "发送消息")
    public Result<Message> sendMessage(@Valid @RequestBody MessageDTO messageDTO, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return messageService.sendMessage(userId, messageDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取消息详情")
    public Result<Message> getMessage(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return messageService.getMessage(id);
    }

    @GetMapping("/conversation/{conversationId}")
    @Operation(summary = "获取会话消息列表")
    public Result<List<Message>> getConversationMessages(@PathVariable Long conversationId, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return messageService.getConversationMessages(userId, conversationId);
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "标记消息为已读")
    public Result<Void> markMessageAsRead(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return messageService.markMessageAsRead(userId, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除消息")
    public Result<Void> deleteMessage(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        return messageService.deleteMessage(userId, id);
    }
} 