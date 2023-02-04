package com.ielts.assistance.config;

import io.github.chatgpt.property.ChatgptProperties;
import io.github.chatgpt.service.impl.DefaultChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(ChatgptProperties.class)
public class ChatGPTAutoConfiguration {
    private final ChatgptProperties chatgptProperties;

    public ChatGPTAutoConfiguration(ChatgptProperties chatgptProperties){
        this.chatgptProperties = chatgptProperties;
        log.debug("chatgpt-springboot-starter loaded.");
    }

    @Bean
    @ConditionalOnMissingBean(io.github.wrangz.chatgpt.service.ChatgptService.class)
    public io.github.wrangz.chatgpt.service.ChatgptService chatgptService(){
        return new DefaultChatgptService(chatgptProperties);
    }
}
