package com.ielts.assistance.controllers;

import com.ielts.assistance.model.ChatGptRequest;
import com.ielts.assistance.model.ResponseObject;
import io.github.chatgpt.service.ChatgptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class ChatGPTController {
    private final ChatgptService chatgptService;
    @GetMapping("/send")
    public ResponseEntity<ResponseObject> send(@RequestParam @Validated String message) {
        try {
            String responseMessage = chatgptService.sendMessage(message);
            return ResponseEntity.ok(new ResponseObject(responseMessage.trim()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject(e.getMessage()));
        }
    }

    @PostMapping("/ielts_essay_evaluation")
    public ResponseEntity<ResponseObject> ieltsScoring(@RequestBody @Validated ChatGptRequest request) {
        try {
            String ieltsScoringRequest = String.format("Give me 4 criteria band score number of the essay can get in IELTS \"%s\"",  request.getMessage());
            String responseMessage = chatgptService.sendMessage(ieltsScoringRequest);
            return ResponseEntity.ok(new ResponseObject(responseMessage.trim()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject(e.getMessage()));
        }
    }
}
