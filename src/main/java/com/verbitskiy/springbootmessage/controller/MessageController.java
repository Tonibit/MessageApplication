package com.verbitskiy.springbootmessage.controller;

import com.verbitskiy.springbootmessage.domain.Message;
import com.verbitskiy.springbootmessage.dto.MessageDTO;
import com.verbitskiy.springbootmessage.dto.form.RequestJsonForm;
import com.verbitskiy.springbootmessage.service.interfaces.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Приложение слушает на эндпоинт /message и если запрос POST и пользователь авторизовался с помощью JWT,
 * то происходит сохранение переданного сообщения.
 * Если же приходит запрос GET и формат сообщения в JSON : "message" : "history 10" (или любое другое число
 * после history), то выдаются последние n сообщений пользователя*/
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessages(@RequestBody RequestJsonForm form) {
        return ResponseEntity.ok().body(messageService.getMessages(form));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> saveMessage(@RequestBody RequestJsonForm form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.saveMessage(form));
    }
}
