package com.solo.soloProject.test;

import com.google.gson.Gson;
import com.solo.soloProject.todo.controller.TodoController;
import com.solo.soloProject.todo.dto.RequestDto;
import com.solo.soloProject.todo.dto.ResponseDto;
import com.solo.soloProject.todo.entity.Todo;
import com.solo.soloProject.todo.mapper.TodoMapper;
import com.solo.soloProject.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class TestwithRestdocs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TodoService todoService;

    @MockBean
    private TodoMapper mapper;


    //-----------------------------------------------------------------------//

    @Test
    public void postTest() throws Exception {

        //i given

        // Request Body ??????
        RequestDto requestDto = new RequestDto("????????????", 1L, false);
        String content = gson.toJson(requestDto);

        // Response Body ??????
        ResponseDto responseDto = new ResponseDto(1L, "????????????", 1L, false, "http://localhost:8080/1");

        // Mock ????????? ????????? Data Stubbing
        given(mapper.requestToTodo(Mockito.any(RequestDto.class))).willReturn(new Todo());
        given(todoService.createTodo(Mockito.any(Todo.class))).willReturn(new Todo());
        given(mapper.todoToResponseDto(Mockito.any(Todo.class))).willReturn(responseDto);

        //i when

        // ????????? ???????????? Post Request ??????
        ResultActions actions = mockMvc.perform(post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //i then

        // Response??? ?????? ????????? ??????
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(requestDto.getTitle()))
                .andExpect(jsonPath("$.order").value(requestDto.getOrder()))
                .andExpect(jsonPath("$.completed").value(requestDto.getCompleted()))
                // ================== API ????????? ======================
                .andDo(document(
                        "post-todo",  // API ?????? ???????????? ????????? ??????
                        preprocessRequest(prettyPrint()), // request??? ???????????? ???????????? ?????????
                        preprocessResponse(prettyPrint()), // response??? ???????????? ???????????? ?????????
                        requestFields( // ????????? ????????? Request Body ????????? ??????
                                List.of(
                                    fieldWithPath("title").type(JsonFieldType.STRING).description("?????? ??? ???"),
                                    fieldWithPath("order").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                    fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("?????? ??????").optional()
                                )
                        ),
                        responseFields( // ????????? ????????? Response Body ????????? ??????
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Todo ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("?????? ??? ???"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                                        fieldWithPath("url").type(JsonFieldType.STRING).description("url ??????")
                                )
                        )
                ));
    }

    @Test
    public void getTodoTest() throws Exception {
        //given
        Long id = 1L;

        ResponseDto responseDto = new ResponseDto(1L, "????????????", 1L, false, "http://localhost:8080/1");

        given(todoService.findTodo(Mockito.anyLong())).willReturn(new Todo());
        given(mapper.todoToResponseDto(Mockito.any(Todo.class))).willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.order").value(responseDto.getOrder()))
                .andExpect(jsonPath("$.completed").value(responseDto.getCompleted()))
                .andDo(document("get-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Todo ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Todo ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("?????? ??? ???"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                                        fieldWithPath("url").type(JsonFieldType.STRING).description("url ??????")
                                )
                        )
                ));
    }

    @Test
    public void getTodosTest() throws Exception {
        //given
        Todo todo1 = new Todo(1L, "?????????", 1L, false);
        Todo todo2 = new Todo(2L, "????????????", 2L, false);
        Todo todo3 = new Todo(3L, "????????????", 3L, false);

        List<ResponseDto> responseDtos = List.of(
                new ResponseDto(1L, "?????????", 1L, false, "http://localhost:8080/1"),
                new ResponseDto(2L, "????????????", 2L, false, "http://localhost:8080/2"),
                new ResponseDto(3L, "????????????", 3L, false, "http://localhost:8080/3")
        );

        given(todoService.findTodos()).willReturn(new ArrayList<>());
        given(mapper.todosToResponses(Mockito.anyList())).willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(responseDtos.size()))
                .andExpect(jsonPath("$.[0].id").value(responseDtos.get(0).getId()))
                .andExpect(jsonPath("$.[0].title").value(responseDtos.get(0).getTitle()))
                .andExpect(jsonPath("$.[0].order").value(responseDtos.get(0).getOrder()))
                .andExpect(jsonPath("$.[0].completed").value(responseDtos.get(0).getCompleted()))
                .andDo(document("get-todos",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Todo ?????????"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("?????? ??? ???"),
                                        fieldWithPath("[].order").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("[].completed").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                                        fieldWithPath("[].url").type(JsonFieldType.STRING).description("url ??????")
                                )
                        )
                ));
    }

    @Test
    public void patchTodoTest() throws Exception {
        //given
        Long id = 1L;
        RequestDto requestDto = new RequestDto("????????????", 1L, false);
        String content = gson.toJson(requestDto);
        ResponseDto responseDto = new ResponseDto(1L, "????????????", 1L, false, "http://localhost:8080/1");

        given(todoService.updateTodo(Mockito.anyLong(), Mockito.any(RequestDto.class))).willReturn(new Todo());
        given(mapper.todoToResponseDto(Mockito.any(Todo.class))).willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(requestDto.getTitle()))
                .andExpect(jsonPath("$.order").value(requestDto.getOrder()))
                .andExpect(jsonPath("$.completed").value(requestDto.getCompleted()))
                .andDo(document("patch-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Todo ?????????")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("?????? ??? ???").optional(),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("????????????").optional(),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("?????? ??????").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Todo ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("?????? ??? ???"),
                                        fieldWithPath("order").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                                        fieldWithPath("url").type(JsonFieldType.STRING).description("url ??????")
                                )
                        )
                ));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        //given
        Long id = 1L;
        doNothing().when(todoService).deleteTodo(Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/{id}", id)
        );

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("delete-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Todo ?????????")
                        )
                ));
    }

    @Test
    public void deleteAllTest() throws Exception {
        //given
        doNothing().when(todoService).deleteTodos();

        //when
        ResultActions actions = mockMvc.perform(
                delete("/")
        );

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("delete-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}
