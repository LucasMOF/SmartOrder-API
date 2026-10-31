package com.lucasmof.smartorder_parser.controller;

import com.lucasmof.smartorder_parser.dto.ParsedOrderResponse;
import com.lucasmof.smartorder_parser.dto.RawOrderRequest;
import com.lucasmof.smartorder_parser.service.OrderParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderParserService orderParserService;

    // Injeção de dependência via construtor
    public OrderController(OrderParserService orderParserService) {
        this.orderParserService = orderParserService;
    }

    @PostMapping("/parse")
    public ResponseEntity<ParsedOrderResponse> parse(@RequestBody RawOrderRequest request) {
        // Envia o texto bagunçado para o serviço e devolve o JSON estruturado com status 200 OK
        ParsedOrderResponse response = orderParserService.parseOrder(request.rawText());
        return ResponseEntity.ok(response);
    }

}
