package com.lucasmof.smartorder_parser.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Record interno para padronizar a resposta de erro
    public record ErrorResponse(String timestamp, int status, String error, String message) {}

    // Captura erros de comunicação com a API do Gemini (Timeouts, 404, 500 da Google, etc.)
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> handleRestClientException(RestClientException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_GATEWAY.value(),
                "Bad Gateway - Falha de Integração com IA",
                "O serviço de IA está temporariamente indisponível. Detalhe: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    // Captura qualquer outro erro inesperado (ex: NullPointerException, JSON inválido)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado ao processar o pedido."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
