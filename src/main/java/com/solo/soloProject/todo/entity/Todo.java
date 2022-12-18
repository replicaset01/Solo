package com.solo.soloProject.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Column(nullable = false, length = 15)
    private String title;

    @Column(nullable = false, length = 10, name = "todo_order")
    private Long order;

    @Column(nullable = false)
    private Boolean completed;

}
