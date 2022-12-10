package com.solo.soloProject.todo.mapper;

import com.solo.soloProject.todo.dto.TodoDto;
import com.solo.soloProject.todo.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    Todo TodoPostToTodo(TodoDto.Post post);

    Todo TodoPatchToTodo(TodoDto.Patch patch);

    TodoDto.Response TodoToTodoResponse(Todo todo);

    List<TodoDto.Response> TodoToTodoResponses(List<Todo> todos);
}
