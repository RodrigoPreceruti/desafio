package com.example.desafio.service;

import com.example.desafio.dto.TaskCreateDTO;
import com.example.desafio.dto.TaskEntityDTO;
import com.example.desafio.dto.TaskUpdateDTO;
import com.example.desafio.entity.Project;
import com.example.desafio.entity.Task;
import com.example.desafio.mapper.TaskMapper;
import com.example.desafio.repository.ProjectRepository;
import com.example.desafio.repository.TaskRepository;
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

    public List<TaskEntityDTO> getAll() {
        List<Task> taskList = this.repository.findAll();

        return this.mapper.toTaskDTOList(taskList);
    }

    public TaskEntityDTO createTask(TaskCreateDTO request) {
        Project project = this.projectRepository
                .findById(request.projectId())
                .orElseThrow();

        Task task = this.mapper.toEntity(request);
        task.setProject(project);

        this.repository.save(task);

        return this.mapper.toResponseDTO(task);
    }

    public TaskEntityDTO updateTask(Long id, TaskUpdateDTO request) {
        Task task = this.repository
                .findById(id)
                .orElseThrow();

        task.setStatus(request.status());

        this.repository.save(task);

        return this.mapper.toResponseDTO(task);
    }

    public void deleteTask(Long id) {
        Task task = this.repository
                .findById(id)
                .orElseThrow();

        this.repository.delete(task);
    }
}
