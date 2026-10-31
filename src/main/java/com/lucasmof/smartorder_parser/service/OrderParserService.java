package com.lucasmof.smartorder_parser.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucasmof.smartorder_parser.dto.ParsedOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmof.smartorder_parser.integration.gemini.GeminiApiClient;
import org.springframework.stereotype.Service;

@Service
public class OrderParserService {

    private final GeminiApiClient geminiApiClient;
    private final ObjectMapper objectMapper;

    // Construtor obrigatório para o Spring injetar as dependências
    public OrderParserService(GeminiApiClient geminiApiClient, ObjectMapper objectMapper) {
        this.geminiApiClient = geminiApiClient;
        this.objectMapper = objectMapper;
    }

    public ParsedOrderResponse parseOrder(String rawText) {
        String prompt = """
                Você é um assistente de IA especializado em extrair dados de pedidos de delivery a partir de textos desestruturados.
                Extraia os itens do pedido a seguir e retorne ESTRITAMENTE um JSON válido, sem formatação markdown (como ```json) ou texto adicional.
                
                O formato exato esperado é:
                {
                  "itens": [
                    {
                      "produto": "nome do produto",
                      "quantidade": número inteiro,
                      "observacoes": "detalhes, restrições ou adições. Deixe vazio se não houver"
                    }
                  ]
                }
                
                Pedido do cliente:
                """ + rawText;

        String jsonResponse = geminiApiClient.generateContent(prompt);

        String cleanJson = jsonResponse.replace("```json", "").replace("```", "").trim();

        try {
            return objectMapper.readValue(cleanJson, ParsedOrderResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao processar a resposta da IA: " + e.getMessage());
        }
    }
}