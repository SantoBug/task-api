package com.douglas.api_cach_redis.repository;

import com.douglas.api_cach_redis.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}