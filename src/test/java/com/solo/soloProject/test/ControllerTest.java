//package com.solo.soloProject.test;
//
//import com.google.gson.Gson;
//import com.jayway.jsonpath.JsonPath;
//import com.solo.soloProject.todo.entity.Todo;
//import com.solo.soloProject.todo.repository.TodoRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//
//import static org.assertj.core.api.BDDAssumptions.given;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.startsWith;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private Gson gson;
//    @Autowired
//    private TodoRepository todoRepository;
//
//
//    /* Post Todo Test */
//
//    @Test
//    void postTodoTest() throws Exception {
//
//        //i given RequestBody -> Json 변환
//        TodoDto.Post post = new TodoDto.Post("abc",1, false);
//
//        String content = gson.toJson(post);
//
//        /* i when
//         * Controller의 핸들러 메소드에 요청을 전송하기 위해서 perform()를 호출해야 하며,
//         * perform() 내부에 Controller 호출을 위한 세부 정보 포함
//         *
//         * MockMvcRequestBuilders 클래스를 이용해 빌더패턴으로 HTTP Request 정보 입력 = perform
//         * post() = HTTP Method + Request URL 설정
//         * accpet() = 클라이언트에서 응답 받을 데이터 타입 설정
//         * contentType() = 서버에서 처리 가능한 데이터 타입 지정
//         * content() = Request Body 데이터 지정
//         */
//
//        ResultActions actions = mockMvc.perform(
//                post("/v1/todos")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content));
//
//        /* i then
//         * when의 perform()은 ResultActions 타입의 객체를 리턴
//         * 이 ResultActions 객체를 이용해 테스트로 전송한 Request에 대한 검증 수행
//         * 첫 andExpect()에서 Matcher를 이용해 예상되는 기대 값 검증
//         * status().isCreated() = Response Status가 201인지 검증
//         *
//         * header().string("", is(startsWith("URI")))에 대한 설명
//         * HTTP Header에 추가된 Localtion의 문자열 값이 "/v1/todos"로 시작하는지 검증
//         */
//        actions.andExpect(status().isCreated())
//                .andExpect(header().string("Location", is(startsWith("/v1/todos"))));
//    }
//
//    /* Patch Todo Test */
//
//    @Test
//    void patchMemberTest() throws Exception {
//
//        Todo member = new Todo("abc", 1, false);
//        Todo savedTodo = todoRepository.save(member);
//        long todoId = savedTodo.getTodoId();
//
//        TodoDto.Patch patch = new TodoDto.Patch(1, "aaa", 2, true);
//
//        String patchContent = gson.toJson(patch);
//
//        URI patchUri = UriComponentsBuilder.newInstance().path("/v1/todos/{todo-id}").buildAndExpand(todoId).toUri();
//
//        ResultActions actions = mockMvc.perform(patch(patchUri)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(patchContent));
//
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value(patch.getTitle()))
//                .andExpect(jsonPath("$.data.order").value(patch.getOrder()))
//                .andExpect(jsonPath("$.data.completed").value(patch.isCompleted()));
//    }
//
//    /* Get Todo Test */
//
//    @Test
//    void getMemberTest() throws Exception {
//
//        TodoDto.Post post = new TodoDto.Post("abc", 1, false);
//        String postContent = gson.toJson(post);
//
//        ResultActions postActions = mockMvc.perform(post("/v1/todos")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(postContent));
//
//        long memberId;
//
//        //i postMember()의 response에 전달되는 Location Header를 가져옴 = "/v1/todos/1
//        String location = postActions.andReturn().getResponse().getHeader("Location");
//
//        //i 위에서 얻은 Location Header 값 중 memberId에 해당하는 부분만 추출
//        memberId = Long.parseLong(location.substring(location.lastIndexOf("/")+1));
//
//        //i Build Request URI
//        URI getUri = UriComponentsBuilder.newInstance().path("/v11/members/{member-id}").buildAndExpand(memberId).toUri();
//
//        mockMvc.perform(
//                //i 기대 HTTP Status가 200 인지 검증
//                get(getUri).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                //i Response Body의 프로퍼티가 응답으로 받은 프로퍼티의 값과 일지하는지 검증
//                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
//                .andExpect(jsonPath("$.data.order").value(post.getOrder()));
//    }
//
//    /* Get Todos Test */
//    @Test
//    @DisplayName("Get Todos Test")
//    public void getTodosTest() throws Exception {
//        Todo todo1 = new Todo("abc", 1, false);
//        Todo todo2 = new Todo("aaa", 2, false);
//        Todo save1 = todoRepository.save(todo1);
//        Todo save2 = todoRepository.save(todo2);
//
//        long todoId1 = save1.getTodoId();
//        long todoId2 = save2.getTodoId();
//
//        String page = "1";
//        String size = "5";
//        MultiValueMap<String, String> queryparams = new LinkedMultiValueMap<>();
//        queryparams.add("page", page);
//        queryparams.add("size", size);
//
//        URI getUri = UriComponentsBuilder.newInstance().path("/v1/todos").build().toUri();
//
//        ResultActions actions = mockMvc.perform(get(getUri)
//                .params(queryparams)
//                .accept(MediaType.APPLICATION_JSON));
//
//        MvcResult result = actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").isArray())
//                .andReturn();
//
//        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
//
//        assertThat(list.size(), is(2));
//    }
//
//    /* Delete Todo Test */
//
//    @Test
//    @DisplayName("Delete Todo Test")
//    public void deleteTodoTest() throws Exception {
//        Todo todo1 = new Todo("abc",1,false);
//
//        Todo saveTodo = todoRepository.save(todo1);
//
//        long todoId = saveTodo.getTodoId();
//
//        URI uri = UriComponentsBuilder.newInstance().path("/v1/todos/{todo-id}").buildAndExpand(todoId).toUri();
//
//        mockMvc.perform(delete(uri))
//                .andExpect(status().isNoContent());
//    }
//}
