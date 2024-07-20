package com.tek.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
public class Employee {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nationalId;

    @Column(nullable = false)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private com.tek.entity.Branch branch;
}