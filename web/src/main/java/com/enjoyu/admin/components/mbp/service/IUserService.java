package com.enjoyu.admin.components.mbp.service;

import com.enjoyu.admin.components.mbp.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员用户表 服务类
 * </p>
 *
 * @author mbp
 * @since 2021-12-23
 */
public interface IUserService extends IService<User> {
    User user(String userName);
}
