package com.enjoyu.admin.web.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PageRequest {
    @NotNull
    @Min(value = 0L, message = "页码必须大于0")
    private Integer pageIndex;
    @NotNull
    private Integer pageSize;

}
