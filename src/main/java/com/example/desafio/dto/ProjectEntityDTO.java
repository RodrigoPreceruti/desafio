package com.example.desafio.dto;

import java.time.LocalDate;

public record ProjectEntityDTO(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
