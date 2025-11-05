package com.example.desafio.service;

import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.dto.TaskCreateDTO;
import com.example.desafio.dto.TaskEntityDTO;
import com.example.desafio.dto.TaskUpdateDTO;
import com.example.desafio.entity.PriorityTask;
import com.example.desafio.entity.Project;
import com.example.desafio.entity.StatusTask;
import com.example.desafio.entity.Task;
import com.example.desafio.exception.custom.TaskNotFoundException;
import com.example.desafio.mapper.TaskMapper;
import com.example.desafio.repository.ProjectRepository;
import com.example.desafio.repository.TaskRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService service;

    private Project projectBuilder;

    private Task taskBuilder;

    @BeforeEach
    public void setUp() {
        projectBuilder = Project.builder()
                .id(1L)
                .name("project test")
                .description("test description")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        taskBuilder = Task.builder()
                .id(1L)
                .title("test title")
                .description("test description")
                .status(StatusTask.TODO)
                .priority(PriorityTask.LOW)
                .dueDate(LocalDate.now())
                .project(projectBuilder)
                .build();
    }

    @Test
    void shouldGetAll() {
        var pageable = PageRequest.of(0, 2);

        var task = taskBuilder;

        var taskResponse = new TaskEntityDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), task.getDueDate(), any());

        var taskPageable = new PageImpl<>(List.of(task));

        when(this.repository.findAll(pageable)).thenReturn(taskPageable);
        when(this.mapper.toResponseDTO(task)).thenReturn(taskResponse);

        var result = this.service.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(task.getTitle(), result.getContent().getFirst().title());
        assertEquals(task.getStatus(), result.getContent().getFirst().status());
    }

    @Test
    void shouldCreateTask() {
        var taskCreateDTO = new TaskCreateDTO("test title", "test description", StatusTask.TODO,
                PriorityTask.LOW, LocalDate.now(), 1L);

        var project = projectBuilder;

        var task = taskBuilder;

        var projectResponse = new ProjectEntityDTO(project.getId(), project.getName(), project.getDescription(),
                project.getStartDate(), project.getEndDate());

        var taskResponse = new TaskEntityDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), task.getDueDate(), projectResponse);

        when(this.projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(this.mapper.toEntity(taskCreateDTO)).thenReturn(task);
        when(this.mapper.toResponseDTO(task)).thenReturn(taskResponse);
        when(this.repository.save(any(Task.class))).thenReturn(task);

        var result = this.service.createTask(taskCreateDTO);

        assertEquals(result.title(), task.getTitle());
        assertEquals(result.description(), task.getDescription());
        assertEquals(result.project().name(), task.getProject().getName());
    }

    @Test
    void shouldUpdateTask() {
        var taskUpdateDTO = new TaskUpdateDTO(StatusTask.DONE);

        var task = taskBuilder;
        task.setStatus(taskUpdateDTO.status());

        var taskResponse = new TaskEntityDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),
                task.getPriority(), task.getDueDate(), any());

        when(this.repository.findById(1L)).thenReturn(Optional.of(task));
        when(this.mapper.toResponseDTO(task)).thenReturn(taskResponse);
        when(this.repository.save(any(Task.class))).thenReturn(task);

        TaskEntityDTO result = this.service.updateTask(1L, taskUpdateDTO);

        assertEquals(result.status(), task.getStatus());
    }

    @Test
    void shouldDeleteTask() {
        var task = taskBuilder;

        when(this.repository.findById(1L)).thenReturn(Optional.of(task));

        this.service.deleteTask(1L);

        verify(this.repository).delete(task);
    }

    @Test
    void shouldThrowTaskNotFoundException() {
        when(this.repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> this.service.deleteTask(1L)
        );
    }
}
