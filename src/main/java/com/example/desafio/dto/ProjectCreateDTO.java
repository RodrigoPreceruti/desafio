package com.example.desafio.dto;

import java.time.LocalDate;

public record ProjectCreateDTO(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
