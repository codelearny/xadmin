package com.enjoyu.admin.integration.mapstruct;

import com.enjoyu.admin.component.auth.entity.Role;
import com.enjoyu.admin.component.auth.entity.User;
import com.enjoyu.admin.component.mapstruct.UserConverter;
import com.enjoyu.admin.component.mapstruct.UserDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;

public class CopyTest {
    @Test
    public void test() {
        User user = new User();
        user.setId(1);
        user.setUsername("hello");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        user.setPhone("13131313311");
        user.setStatus(false);
        Role role = new Role();
        role.setRole("DO");
        user.setRoles(Collections.singleton(role));
        UserDto userDto = UserConverter.INSTANCE.toDto(user);
        System.out.println(userDto);
    }
}
