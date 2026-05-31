package com.douglas.api_cach_redis.controller;

import com.douglas.api_cach_redis.service.TaskService;
import com.douglas.api_cach_redis.entity.Task;
import java.util.List;

import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/criarTask")
    public ResponseEntity<Task> criarTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.criarTask(task));
    }

    @GetMapping("/listarTask")
    public ResponseEntity<List<Task>> listarTask() {
        return ResponseEntity.ok(taskService.listarTask());
    }

}
