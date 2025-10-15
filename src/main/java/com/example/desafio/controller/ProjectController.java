package com.example.desafio.controller;

import com.example.desafio.dto.ProjectCreateDTO;
import com.example.desafio.dto.ProjectEntityDTO;
import com.example.desafio.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProjectEntityDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @PostMapping
    public ResponseEntity<ProjectEntityDTO> createProject(@RequestBody @Valid ProjectCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createProject(request));
    }
}
