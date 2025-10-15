package com.example.desafio.dto;

import com.example.desafio.entity.PriorityTask;
import com.example.desafio.entity.StatusTask;

import java.time.LocalDate;

public record TaskCreateDTO(
        String title,
        String description,
        StatusTask status,
        PriorityTask priority,
        LocalDate dueDate,
        Long projectId
) {
}
