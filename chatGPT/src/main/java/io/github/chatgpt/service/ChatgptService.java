package io.github.wrangz.chatgpt.service;

import io.github.chatgpt.dto.ChatRequest;
import io.github.wrangz.chatgpt.dto.ChatResponse;

public interface ChatgptService {

    String sendMessage(String message);

    ChatResponse sendChatRequest(ChatRequest request);

}
