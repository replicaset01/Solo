package com.solo.soloProject.todo.repository;

import com.solo.soloProject.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findById(long todoId);
}
