package com.example.desafio.mapper;

import com.example.desafio.dto.TaskCreateDTO;
import com.example.desafio.dto.TaskEntityDTO;
import com.example.desafio.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskCreateDTO request);

    @Mapping(target = "project.id", source = "project.id")
    @Mapping(target = "project.name", source = "project.name")
    @Mapping(target = "project.description", source = "project.description")
    @Mapping(target = "project.startDate", source = "project.startDate")
    @Mapping(target = "project.endDate", source = "project.endDate")
    TaskEntityDTO toResponseDTO(Task task);
}
