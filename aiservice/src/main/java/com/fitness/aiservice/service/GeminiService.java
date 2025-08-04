package com.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    public String getAnswer(String question) {
        return webClient.post()
                .uri(geminiApiUrl)
                .header("X-goog-api-key", geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(Map.of("contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", question)})
                }))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }



}
