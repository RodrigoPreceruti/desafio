package com.example.desafio.service;

import com.example.desafio.dto.TaskCreateDTO;
import com.example.desafio.dto.TaskEntityDTO;
import com.example.desafio.dto.TaskUpdateDTO;
import com.example.desafio.entity.Project;
import com.example.desafio.entity.Task;
import com.example.desafio.exception.custom.ProjectNotFoundException;
import com.example.desafio.exception.custom.TaskNotFoundException;
import com.example.desafio.mapper.TaskMapper;
import com.example.desafio.repository.ProjectRepository;
import com.example.desafio.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final ProjectRepository projectRepository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, ProjectRepository projectRepository, TaskMapper mapper) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    public Page<TaskEntityDTO> getAll(Pageable pageable) {
        Page<Task> taskList = this.repository.findAll(pageable);

        return taskList.map(mapper::toResponseDTO);
    }

    public TaskEntityDTO createTask(TaskCreateDTO request) {
        Project project = this.projectRepository
                .findById(request.projectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        Task task = this.mapper.toEntity(request);
        task.setProject(project);

        this.repository.save(task);

        return this.mapper.toResponseDTO(task);
    }

    public TaskEntityDTO updateTask(Long id, TaskUpdateDTO request) {
        Task task = this.repository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        task.setStatus(request.status());

        this.repository.save(task);

        return this.mapper.toResponseDTO(task);
    }

    public void deleteTask(Long id) {
        Task task = this.repository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        this.repository.delete(task);
    }
}
