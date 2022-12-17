package com.solo.soloProject.todo.service;

import com.solo.soloProject.todo.entity.Todo;
import com.solo.soloProject.error.BusinessException;
import com.solo.soloProject.error.ExceptionCode;
import com.solo.soloProject.todo.repository.TodoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        verifyExistTodo(todo.getTodoId());
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo;
    }

    public Todo updateTodo(Todo todo) {
        Todo findTodo = verifyExistTodo(todo.getTodoId());


        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));
        Optional.ofNullable(todo.getOrder())
                .ifPresent(order -> findTodo.setOrder(order));


        findTodo.setCompleted(true);
        findTodo.setModifiedAt(LocalDateTime.now());
        return todoRepository.save(findTodo);
    }

    public Todo findTodo(long todoId) {
        return verifyExistTodo(todoId);
    }

    public Page<Todo> findTodos(int page, int size) {
        return todoRepository.findAll(PageRequest.of(page,size, Sort.by("todoId").descending()));
    }

    public void deleteTodo(long todoId) {
        Todo findTodo = verifyExistTodo(todoId);
        todoRepository.delete(findTodo);
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
