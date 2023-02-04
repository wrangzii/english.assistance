package io.github.wrangz.chatgpt.dto;

import io.github.chatgpt.dto.Choice;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ChatResponse {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;
    private io.github.wrangz.chatgpt.dto.Usage usage;

}
