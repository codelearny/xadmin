package com.enjoyu.admin.repository;

import com.enjoyu.admin.components.mbp.entity.Role;
import com.enjoyu.admin.components.mbp.entity.UserRole;
import com.enjoyu.admin.components.mbp.service.IRoleService;
import com.enjoyu.admin.components.mbp.service.IUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysRoleRepository {
    IRoleService roleService;
    IUserRoleService userRoleService;

    public List<Role> allRoles() {
        return roleService.list();
    }

    public List<Role> getRolesByUserId(Long userId) {
        List<UserRole> userRoles = userRoleService.lambdaQuery().eq(UserRole::getUserId, userId).list();
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> collect = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return roleService.listByIds(collect);
    }

}
