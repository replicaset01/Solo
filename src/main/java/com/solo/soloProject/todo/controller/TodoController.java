package com.solo.soloProject.todo.controller;

import com.solo.soloProject.todo.dto.RequestDto;
import com.solo.soloProject.todo.entity.Todo;
import com.solo.soloProject.todo.mapper.TodoMapper;
import com.solo.soloProject.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin("https://todobackend.com")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final TodoMapper mapper;

    // Todo 등록
    @PostMapping
    public ResponseEntity postTodo(@RequestBody RequestDto requestDto) {
        Todo createTodo = todoService.createTodo(mapper.requestToTodo(requestDto));

        return new ResponseEntity<>(mapper.todoToResponseDto(createTodo), HttpStatus.CREATED);
    }

    // Todo 1개 조회
    @GetMapping
    public ResponseEntity getTodos() {
        List<Todo> todos = todoService.findTodos();
        return new ResponseEntity<>(mapper.todosToResponses(todos), HttpStatus.OK);
    }

    // Todo 전체 조회
    @GetMapping("{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") Long todoId) {
        Todo todo = todoService.findTodo(todoId);

        return new ResponseEntity<>(mapper.todoToResponseDto(todo), HttpStatus.OK);
    }

    // Todo 수정
    @PatchMapping("{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") Long todoId, @RequestBody RequestDto requestDto) {
        Todo updateTodo = todoService.updateTodo(todoId, requestDto);

        return new ResponseEntity<>(mapper.todoToResponseDto(updateTodo), HttpStatus.OK);
    }

    // Todo 1개 삭제
    @DeleteMapping("{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") Long todoId) {
        todoService.deleteTodo(todoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Todo 전체 삭제
    @DeleteMapping
    public ResponseEntity deleteAll() {
        todoService.deleteTodos();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
