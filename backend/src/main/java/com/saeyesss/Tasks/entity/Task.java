package com.saeyesss.Tasks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saeyesss.Tasks.enums.Priority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Column(length = 1000)
    private String description;
    @NotNull
    private Boolean completed;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;


}
