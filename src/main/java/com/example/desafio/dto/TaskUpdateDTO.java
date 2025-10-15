package com.example.desafio.dto;

import com.example.desafio.entity.StatusTask;

public record TaskUpdateDTO(
        StatusTask status
) {
}
