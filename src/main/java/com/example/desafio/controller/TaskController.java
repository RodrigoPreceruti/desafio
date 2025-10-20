package com.example.desafio.controller;

import com.example.desafio.dto.TaskCreateDTO;
import com.example.desafio.dto.TaskEntityDTO;
import com.example.desafio.dto.TaskUpdateDTO;
import com.example.desafio.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<TaskEntityDTO>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<TaskEntityDTO> createTask(@RequestBody @Valid TaskCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createTask(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskEntityDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        this.service.deleteTask(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
