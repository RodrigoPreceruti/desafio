package com.example.desafio.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionResponseDTO(
        Integer status,
        String message,
        LocalDateTime timestamp,
        String path,
        List<String> details
) {
}
