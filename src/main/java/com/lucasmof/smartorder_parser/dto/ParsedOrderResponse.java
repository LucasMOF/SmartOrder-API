package com.lucasmof.smartorder_parser.dto;

import java.util.List;

public record ParsedOrderResponse(
        List<OrderItemDTO> itens
) {
}
