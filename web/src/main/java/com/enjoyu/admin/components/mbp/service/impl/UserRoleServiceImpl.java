package com.enjoyu.admin.components.mbp.service.impl;

import com.enjoyu.admin.components.mbp.entity.UserRole;
import com.enjoyu.admin.components.mbp.mapper.UserRoleMapper;
import com.enjoyu.admin.components.mbp.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员用户-角色关联表 服务实现类
 * </p>
 *
 * @author mbp
 * @since 2022-01-04
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
