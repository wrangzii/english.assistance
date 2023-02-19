package io.github.chatgpt.service.impl;

import io.github.chatgpt.dto.ChatRequest;
import io.github.chatgpt.dto.ChatResponse;
import io.github.chatgpt.exception.ChatgptException;
import io.github.chatgpt.property.ChatgptProperties;
import io.github.chatgpt.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class DefaultChatgptService implements ChatgptService {

    protected final ChatgptProperties chatgptProperties;

    private final String URL = "https://api.openai.com/v1/completions";

    private final String AUTHORIZATION;
    @Value("${chatgpt.token}")
    private String token;

    public DefaultChatgptService(ChatgptProperties chatgptProperties) {
        this.chatgptProperties = chatgptProperties;
        AUTHORIZATION = "Bearer " + chatgptProperties.getApiKey();
    }

    @Override
    public String sendMessage(String message) {
//        refreshToken(token);
        ChatRequest chatRequest = new ChatRequest(chatgptProperties.getModel(), message,
                chatgptProperties.getMaxTokens(), chatgptProperties.getTemperature(), chatgptProperties.getTopP());
        ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(chatRequest, chatgptProperties.getApiKey()));
        try {
            return chatResponse.getChoices().get(0).getText();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

//    @Override
//    public ChatResponse sendChatRequest(ChatRequest chatRequest) {
//        return this.getResponse(this.buildHttpEntity(chatRequest, token));
//    }

    public HttpEntity<ChatRequest> buildHttpEntity(ChatRequest chatRequest, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", "Bearer " + token);
        return new HttpEntity<>(chatRequest, headers);
    }

    public ChatResponse getResponse(HttpEntity<ChatRequest> chatRequestHttpEntity) {
        log.info("request url: {}, httpEntity: {}",URL, chatRequestHttpEntity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ChatResponse> responseEntity = restTemplate.postForEntity(URL, chatRequestHttpEntity, ChatResponse.class);
        if (responseEntity.getStatusCode().isError()) {
            log.error("error response status: {}", responseEntity);
            throw new ChatgptException("error response status :" + responseEntity.getStatusCode().value());
        } else {
            log.info("response: {}", responseEntity);
        }
        return responseEntity.getBody();
    }

    public String refreshToken(String token) {
        String authUrl = "https://api.openai.com/v1/auth/tokens/refresh";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-002");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map body = response.getBody();
            // Store the token for future API requests
            return (String) body.get("access_token");
        }
        return null;
    }
}
