package com.ielts.assistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan("com.ielts.assistance")
public class AssistanceApplication {
    public static void main(String[] args){
        SpringApplication.run(AssistanceApplication.class, args);
    }
}
