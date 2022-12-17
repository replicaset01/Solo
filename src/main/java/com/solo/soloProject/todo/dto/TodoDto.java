package com.solo.soloProject.todo.dto;

import com.solo.soloProject.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class TodoDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotEmpty
        private String title;
        @NotNull
        private int order;
        @NotNull
        private boolean completed;


    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @NotNull
        private long todoId;
        @NotEmpty
        private String title;
        @NotNull
        private int order;
        @NotNull
        private boolean completed;


        public void setTodoId(long todoId) {
            this.todoId = todoId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long todoId;
        private String title;
        private int order;
        private boolean completed;
    }
}
