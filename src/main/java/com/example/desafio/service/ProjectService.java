package com.example.desafio.service;

import com.example.desafio.dto.ProjectCreateDTO;
import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.entity.Project;
import com.example.desafio.mapper.ProjectMapper;
import com.example.desafio.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    public ProjectService(ProjectRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProjectEntityDTO> getAll() {
        List<Project> projectList = this.repository.findAll();

        return this.mapper.toListProjectDTO(projectList);
    }

    public ProjectEntityDTO createProject(ProjectCreateDTO request) {
        Project project = this.mapper.toEntity(request);
        this.repository.save(project);

        return this.mapper.toProjectDTO(project);
    }
}
