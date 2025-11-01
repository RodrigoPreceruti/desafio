package com.example.desafio.service;

import com.example.desafio.dto.ProjectCreateDTO;
import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.entity.Project;
import com.example.desafio.mapper.ProjectMapper;
import com.example.desafio.repository.ProjectRepository;
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

    @Test
    void getAll() {
        var pageable = PageRequest.of(0, 2);

        var project1 = Project.builder()
                .id(1L)
                .name("project 1 test")
                .description("project 1 test description")
                .startDate(LocalDate.of(2025, 11, 1))
                .endDate(LocalDate.of(2025, 11, 30))
                .build();

        var project2 = Project.builder()
                .id(1L)
                .name("project 2 test")
                .description("project 2 test description")
                .startDate(LocalDate.of(2025, 11, 10))
                .endDate(LocalDate.of(2025, 12, 15))
                .build();

        var dto1 = new ProjectEntityDTO(project1.getId(), project1.getName(), project1.getDescription(),
                project1.getStartDate(), project1.getEndDate());

        var dto2 = new ProjectEntityDTO(project2.getId(), project2.getName(), project2.getDescription(),
                project2.getStartDate(), project2.getEndDate());

        var projectPage = new PageImpl<>(List.of(project1, project2));

        when(this.repository.findAll(pageable)).thenReturn(projectPage);
        when(this.mapper.toProjectDTO(project1)).thenReturn(dto1);
        when(this.mapper.toProjectDTO(project2)).thenReturn(dto2);

        var result = service.getAll(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("project 1 test", result.getContent().get(0).name());
        assertEquals("project 2 test", result.getContent().get(1).name());
    }

    @Test
    void createProject() {
        var projectDTO = new ProjectCreateDTO("project test", "project description",
                LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 30));

        var projectEntity = Project.builder()
                .id(1L)
                .name(projectDTO.name())
                .description(projectDTO.description())
                .startDate(projectDTO.startDate())
                .endDate(projectDTO.endDate())
                .build();

        var projectResponse = new ProjectEntityDTO(projectEntity.getId(), projectEntity.getName(),
                projectEntity.getDescription(), projectEntity.getStartDate(), projectEntity.getEndDate());

        when(this.mapper.toEntity(projectDTO)).thenReturn(projectEntity);
        when(this.mapper.toProjectDTO(projectEntity)).thenReturn(projectResponse);
        when(this.repository.save(any())).thenReturn(projectEntity);

        var project = this.service.createProject(projectDTO);

        assertEquals(projectResponse.id(), project.id());
        assertEquals(projectResponse.name(), project.name());
        assertEquals(projectResponse.description(), project.description());
        assertEquals(projectResponse.startDate(), project.startDate());
        assertEquals(projectResponse.endDate(), project.endDate());
    }
}
