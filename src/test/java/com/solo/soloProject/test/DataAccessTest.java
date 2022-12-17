package com.solo.soloProject.test;

import com.solo.soloProject.todo.entity.Todo;
import com.solo.soloProject.todo.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DataAccessTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Save 테스트")
    public void saveTotoTest() {
        //i given
        Todo member = new Todo();
        member.setTitle("abc");
        member.setOrder(1);
        member.setCompleted(false);

        //i when
        Todo savedMember = todoRepository.save(member);

        //i then
        assertNotNull(savedMember);
        assertTrue(member.getTitle().equals(savedMember.getTitle()));
//        assertTrue(member.getOrder().equals(savedMember.getOrder()));
//        assertTrue(member.isCompleted().equals(savedMember.isCompleted()));

    }

    @Test
    @DisplayName("Find 테스트")
    public void findByIdTest() {
        //i given
        Todo member = new Todo();
        member.setTitle("abc");
        member.setOrder(1);
        member.setCompleted(false);

        //i when
        todoRepository.save(member);
        Optional<Todo> findTodo = todoRepository.findById(member.getTodoId());

        //i then
        assertTrue(findTodo.isPresent());
        assertTrue(findTodo.get().getTitle().equals(member.getTitle()));
    }
}
