package com.example.desafio.service;

import com.example.desafio.dto.ProjectCreateDTO;
import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.entity.Project;
import com.example.desafio.mapper.ProjectMapper;
import com.example.desafio.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository repository;

    @Mock
    private ProjectMapper mapper;

    @InjectMocks
    private ProjectService service;

    private Project projectBuilder;

    @BeforeEach
    public void setUp() {
        projectBuilder = Project.builder()
                .id(1L)
                .name("project test")
                .description("project test description")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }

    @Test
    void shouldGetAll() {
        var pageable = PageRequest.of(0, 2);

        var project = projectBuilder;

        var projectResponse = new ProjectEntityDTO(project.getId(), project.getName(), project.getDescription(),
                project.getStartDate(), project.getEndDate());

        var projectPage = new PageImpl<>(List.of(project));

        when(this.repository.findAll(pageable)).thenReturn(projectPage);
        when(this.mapper.toProjectDTO(any())).thenReturn(projectResponse);

        var result = service.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(result.getContent().getFirst().name(), project.getName());
        assertEquals(result.getContent().getFirst().description(), project.getDescription());
    }

    @Test
    void shouldCreateProject() {
        var projectCreateDTO = new ProjectCreateDTO("project test", "project description",
                LocalDate.now(), LocalDate.now());

        var projectEntity = projectBuilder;

        var projectResponse = new ProjectEntityDTO(projectEntity.getId(), projectEntity.getName(),
                projectEntity.getDescription(), projectEntity.getStartDate(), projectEntity.getEndDate());

        when(this.mapper.toEntity(projectCreateDTO)).thenReturn(projectEntity);
        when(this.mapper.toProjectDTO(projectEntity)).thenReturn(projectResponse);
        when(this.repository.save(any(Project.class))).thenReturn(projectEntity);

        var project = this.service.createProject(projectCreateDTO);

        assertEquals(projectResponse.id(), project.id());
        assertEquals(projectResponse.name(), project.name());
        assertEquals(projectResponse.description(), project.description());
    }
}
