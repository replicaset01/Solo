package com.solo.soloProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleResDto<T> {
    private T data;
}
