package br.com.lp2.trabalho;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Envia de volta o Erro 400 com a mensagem de problema em texto plano puro
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
