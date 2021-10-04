package com.enjoyu.admin.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enjoyu.admin.dao.SysRoleMapper;
import com.enjoyu.admin.dao.entity.SysRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SysRoleRepository extends ServiceImpl<SysRoleMapper, SysRole> {

    SysUserRoleRepository sysUserRoleRepository;

    public List<SysRole> allRoles() {
        return list();
    }

    public List<SysRole> getRolesByUserId(Long userId) {
        List<Long> roleIds = sysUserRoleRepository.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return listByIds(roleIds);
    }

}
