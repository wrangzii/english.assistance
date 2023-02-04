package com.ielts.assistance.controllers;

import com.ielts.assistance.model.ResponseObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class ChatGPTController {
    private final io.github.wrangz.chatgpt.service.ChatgptService chatgptService;

    @GetMapping("/send")
    public ResponseEntity<ResponseObject> send(@RequestParam @Validated String message,
                                               @RequestParam String token) {
        System.setProperty("TOKEN_API_KEY", token);
        System.out.println(System.getenv("TOKEN_API_KEY"));
        try {
            String responseMessage = chatgptService.sendMessage(message);
            return ResponseEntity.ok(new ResponseObject(responseMessage.trim()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject(e.getMessage()));
        }
    }
}
