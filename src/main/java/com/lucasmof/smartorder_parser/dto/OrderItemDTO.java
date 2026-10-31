package com.lucasmof.smartorder_parser.dto;

public record OrderItemDTO(
        String produto,
        int quantidade,
        String observacoes
) {
}
