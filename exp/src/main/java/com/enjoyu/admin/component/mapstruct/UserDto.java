package com.enjoyu.admin.component.mapstruct;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String phone;
    private String roles;
    private String createDate;
}
