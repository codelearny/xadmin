package com.enjoyu.admin.component.mapstruct;

import com.enjoyu.admin.component.auth.entity.Role;
import com.enjoyu.admin.component.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(source = "username", target = "name")
    @Mapping(target = "roles", expression = "java(roles(user.getRoles()))")
    @Mapping(source = "createTime", target = "createDate", dateFormat = "yyyy-MM-dd")
    UserDto toDto(User user);

    default String roles(Set<Role> roles) {
        return Optional
                .ofNullable(roles)
                .orElse(Collections.emptySet())
                .stream()
                .map(Role::getRole)
                .collect(Collectors.joining());
    }
}
