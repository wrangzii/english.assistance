package com.ielts.assistance.controllers;

import com.ielts.assistance.payload.response.ResponseObject;
import com.ielts.assistance.service.UtilService;
import io.github.chatgpt.service.ChatgptService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ielts.assistance.constants.CommonConstant.GIVE_HIGHER_BAND_ESSAY_EXAMPLE_PREFIX;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class ChatGPTController {
    private final ChatgptService chatgptService;
    private final UtilService utilService;
    @GetMapping(path = "/chatGPT", consumes = "text/plain")
    public ResponseEntity<ResponseObject> send(@RequestParam @Validated String message) {
        try {
            String responseMessage = chatgptService.sendMessage(message);
            return ResponseEntity.ok(new ResponseObject(responseMessage.trim()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject(e.getMessage()));
        }
    }

    @PostMapping(path ="/ielts_essay_evaluation", consumes = "text/plain")
    public ResponseEntity<ResponseObject> ieltsScoring(@RequestBody @Validated @Schema(example = "Please input your essay", minLength = 250) String request) {
        try {
            int wordCount = utilService.countWords(request);
            if(wordCount < 250) {
                return ResponseEntity.badRequest().body(new ResponseObject("Min word of an essay is 250!"));
            }
            String ieltsScoringRequest = String.format("%s\"%s\"", GIVE_HIGHER_BAND_ESSAY_EXAMPLE_PREFIX, request.replace("\n", ""));
            String responseMessage = chatgptService.sendMessage(ieltsScoringRequest);
            return ResponseEntity.ok(new ResponseObject(responseMessage.trim()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject(e.getMessage()));
        }
    }
}
