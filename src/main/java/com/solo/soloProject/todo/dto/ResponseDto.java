package com.solo.soloProject.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;
}
