//package com.solo.soloProject.test;
//
//import com.google.gson.Gson;
//import com.solo.soloProject.todo.entity.Todo;
//import com.solo.soloProject.todo.mapper.TodoMapper;
//import com.solo.soloProject.todo.service.TodoService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.startsWith;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class MockitoTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @MockBean
//    private TodoService todoService;
//
//    @Autowired
//    private TodoMapper mapper;
//
//
//    /* Use Mockito Post Todo Test */
//
//    @Test
//    @DisplayName("Mockito를 이용한 Post Todo Test")
//    public void mockPostTest() throws Exception {
//
//        TodoDto.Post post = new TodoDto.Post("abc", 1, false);
//
//        Todo todo = mapper.TodoPostToTodo(post);
//        todo.setTodoId(1L);
//
//        given(todoService.createTodo(Mockito.any(Todo.class))).willReturn(todo);
//
//        String content = gson.toJson(post);
//
//        ResultActions actions = mockMvc.perform(post("/v1/todos")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content));
//
//        actions.andExpect(status().isCreated())
//                .andExpect(header().string("Location", is(startsWith("/v1/todos"))));
//    }
//}
