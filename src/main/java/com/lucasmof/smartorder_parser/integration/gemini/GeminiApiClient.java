package com.lucasmof.smartorder_parser.integration.gemini;

import com.lucasmof.smartorder_parser.integration.gemini.dto.GeminiRequest;
import com.lucasmof.smartorder_parser.integration.gemini.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiApiClient {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeminiApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateContent(String prompt) {
        // 1. Monta o cabeçalho (Headers)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. Monta o corpo da requisição (Body)
        GeminiRequest requestBody = GeminiRequest.of(prompt);
        HttpEntity<GeminiRequest> request = new HttpEntity<>(requestBody, headers);

        // 3. Monta a URL com a chave de autenticação
        String urlWithKey = apiUrl + "?key=" + apiKey;

        // 4. Faz a chamada POST para o Google
        GeminiResponse response = restTemplate.postForObject(urlWithKey, request, GeminiResponse.class);

        // 5. Retorna apenas o texto extraído da resposta
        return response != null ? response.extractText() : "";
    }
}
