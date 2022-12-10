package com.solo.soloProject.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public MultiResDto(List<T> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(
                page.getNumber()+1,
                pageInfo.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
