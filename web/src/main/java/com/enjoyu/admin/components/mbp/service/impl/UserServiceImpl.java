package com.enjoyu.admin.components.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.mapper.UserMapper;
import com.enjoyu.admin.components.mbp.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员用户表 服务实现类
 * </p>
 *
 * @author mbp
 * @since 2021-12-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User user(String userName) {
        return lambdaQuery().eq(User::getUsername, userName).one();
    }
}
