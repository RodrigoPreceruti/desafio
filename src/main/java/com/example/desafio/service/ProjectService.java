package com.example.desafio.service;

import com.example.desafio.dto.ProjectCreateDTO;
import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.entity.Project;
import com.example.desafio.mapper.ProjectMapper;
import com.example.desafio.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    public ProjectService(ProjectRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<ProjectEntityDTO> getAll(Pageable pageable) {
        Page<Project> projectList = this.repository.findAll(pageable);

        return projectList.map(mapper::toProjectDTO);
    }

    public ProjectEntityDTO createProject(ProjectCreateDTO request) {
        Project project = this.mapper.toEntity(request);
        this.repository.save(project);

        return this.mapper.toProjectDTO(project);
    }
}
