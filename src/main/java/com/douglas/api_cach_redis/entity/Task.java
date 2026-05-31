package com.douglas.api_cach_redis.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Task implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)



    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDate prazo;




}