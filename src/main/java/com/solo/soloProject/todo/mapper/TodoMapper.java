package com.solo.soloProject.todo.mapper;

import com.solo.soloProject.todo.dto.RequestDto;
import com.solo.soloProject.todo.dto.ResponseDto;
import com.solo.soloProject.todo.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TodoMapper {

    //i Mapper를 통해 Dto -> Entity 변환
    public Todo requestToTodo(RequestDto requestDto) {

        if (requestDto == null)
            return null;

        if (ObjectUtils.isEmpty(requestDto.getTitle()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (ObjectUtils.isEmpty(requestDto.getOrder()))
            requestDto.setOrder(0L);

        if (ObjectUtils.isEmpty(requestDto.getCompleted()))
            requestDto.setCompleted(false);

        Todo todo = Todo.builder()
                .title(requestDto.getTitle())
                .order(requestDto.getOrder())
                .completed(requestDto.getCompleted())
                .build();

        return todo;
    }

    public ResponseDto todoToResponseDto(Todo todo) {
        if (todo == null)
            return null;

        Long id = todo.getTodoId();
        String title = todo.getTitle();
        Long order = todo.getOrder();
        Boolean completed = todo.getCompleted();

        ResponseDto response = ResponseDto.builder()
                .id(id)
                .title(title)
                .order(order)
                .completed(completed)
                .url("http://localhost:8080/"+id)
                .build();

        return response;

    }

    public List<ResponseDto> todosToResponses(List<Todo> todos) {
        if (todos == null)
            return null;

        List<ResponseDto> list = new ArrayList<>(todos.size());

        for (Todo todo : todos)
            list.add(todoToResponseDto(todo));

        return list;
    }
}
