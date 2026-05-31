package com.douglas.api_cach_redis.service;


import com.douglas.api_cach_redis.entity.Task;
import com.douglas.api_cach_redis.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TaskService {
     private final TaskRepository taskRepository;

     @CacheEvict(value = "tasks", allEntries = true)
     public Task criarTask(Task task) {
          return taskRepository.save(task);

     }
     @Cacheable("tasks")
     public List<Task> listarTask(){
          return taskRepository.findAll();

     }

     @CacheEvict(value= "tasks", allEntries = true)
     public Task atualizarTask(Task task){
          return taskRepository.save(task);

     }

     @CacheEvict(value = "tasks", allEntries = true)
     public void deletarTask(Task task){
         taskRepository.delete(task);
     }
}
