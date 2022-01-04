package com.enjoyu.admin.components.mbp.service.impl;

import com.enjoyu.admin.components.mbp.entity.Role;
import com.enjoyu.admin.components.mbp.mapper.RoleMapper;
import com.enjoyu.admin.components.mbp.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员角色表 服务实现类
 * </p>
 *
 * @author mbp
 * @since 2022-01-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
