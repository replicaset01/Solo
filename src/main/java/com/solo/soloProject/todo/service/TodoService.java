package com.solo.soloProject.todo.service;

import com.solo.soloProject.todo.dto.RequestDto;
import com.solo.soloProject.todo.entity.Todo;
import com.solo.soloProject.error.BusinessException;
import com.solo.soloProject.error.ExceptionCode;
import com.solo.soloProject.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long todoId, RequestDto dto) {
        Todo findTodo = findTodo(todoId);

        Optional.ofNullable(dto.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));
        Optional.ofNullable(dto.getOrder())
                .ifPresent(order -> findTodo.setOrder(order));
        Optional.ofNullable(dto.getCompleted())
                .ifPresent(completed -> findTodo.setCompleted(completed));

        findTodo.setModifiedAt(LocalDateTime.now());

        return todoRepository.save(findTodo);
    }

    public Todo findTodo(Long todoId) {
        return verifyExistTodo(todoId);
    }

    public List<Todo> findTodos() {
        List<Todo> todos = todoRepository.findAll();

        return todos;
    }

    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public void deleteTodos() {
        todoRepository.deleteAll();
    }

    public Todo verifyExistTodo(long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        Todo todo = optionalTodo.orElseThrow(() ->
                new BusinessException(ExceptionCode.TODO_NOT_FOUND));
        return todoRepository.save(todo);
    }
}
