//package com.solo.soloProject.test;
//
//import com.solo.soloProject.error.BusinessException;
//import com.solo.soloProject.todo.entity.Todo;
//import com.solo.soloProject.todo.repository.TodoRepository;
//import com.solo.soloProject.todo.service.TodoService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class ServiceMockTest {
//
//    @Mock
//    private TodoRepository todoRepository;
//
//    @InjectMocks
//    private TodoService todoService;
//
//    @Test
//    public void createTodoTest() {
//        Todo todo1 = new Todo("abc",1,false);
//
//        given(todoRepository.findById(todo1.getTodoId())).willReturn(Optional.of(todo1));
//
//        assertThrows(BusinessException.class, () -> todoService.createTodo(todo1));
//    }
//}
