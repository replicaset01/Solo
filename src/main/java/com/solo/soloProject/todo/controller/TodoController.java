package com.solo.soloProject.todo.controller;

import com.solo.soloProject.dto.MultiResDto;
import com.solo.soloProject.dto.SingleResDto;
import com.solo.soloProject.todo.dto.TodoDto;
import com.solo.soloProject.todo.entity.Todo;
import com.solo.soloProject.todo.mapper.TodoMapper;
import com.solo.soloProject.todo.repository.TodoRepository;
import com.solo.soloProject.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/todos")
public class TodoController {

    private TodoRepository todoRepository;
    private TodoService todoService;
    private TodoMapper mapper;

    public TodoController(TodoRepository todoRepository, TodoService todoService, TodoMapper maapper) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
        this.mapper = maapper;
    }

    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoDto.Post post) {
        Todo todo = mapper.TodoPostToTodo(post);

        Todo createTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(new SingleResDto<>(mapper.TodoToTodoResponse(createTodo)), HttpStatus.CREATED);
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive long todoId,
                                    @Valid @RequestBody TodoDto.Patch patch) {
        patch.setTodoId(todoId);
        Todo update = todoService.updateTodo(mapper.TodoPatchToTodo(patch));
        return new ResponseEntity<>(new SingleResDto<>(mapper.TodoToTodoResponse(update)), HttpStatus.OK);
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity findTodo(@PathVariable("todo-id") @Positive long todoId) {
        Todo find = todoService.findTodo(todoId);

        return new ResponseEntity(new SingleResDto<>(mapper.TodoToTodoResponse(find)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodos(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<Todo> pageTodo = todoService.findTodos(page -1, size);
        List<Todo> todos = pageTodo.getContent();

        return new ResponseEntity<>(new MultiResDto<>(mapper.TodoToTodoResponses(todos), pageTodo), HttpStatus.OK);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") @Positive long todoId) {
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public void deleteTodos() {
        todoService.deleteTodos();
    }
}
