package com.lucasmof.smartorder_parser.integration.gemini.dto;

import java.util.List;

public record GeminiRequest(List<Content> contents) {

    public record Content(List<Part> parts) {}

    public record Part(String text) {}

    // Método auxiliar para facilitar a criação do objeto
    public static GeminiRequest of(String prompt) {
        return new GeminiRequest(List.of(
                new Content(List.of(new Part(prompt)))
        ));
    }
}
