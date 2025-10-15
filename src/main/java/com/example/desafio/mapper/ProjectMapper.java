package com.example.desafio.mapper;

import com.example.desafio.dto.ProjectCreateDTO;
import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectCreateDTO request);

    ProjectEntityDTO toProjectDTO(Project project);

    List<ProjectEntityDTO> toListProjectDTO(List<Project> projects);
}
