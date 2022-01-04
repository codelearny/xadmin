package com.enjoyu.admin.repository;

import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysUserRepository {
    IUserService userService;

    public User user(String userName) {
        return userService.lambdaQuery().eq(User::getUsername, userName).one();
    }

    public void deleteByUserId(Integer userId) {
        userService.lambdaUpdate().eq(User::getId, userId).remove();
    }

    public void deleteByUserIds(List<Long> userIds) {
        userService.lambdaUpdate().in(User::getId, userIds).remove();
    }

}
