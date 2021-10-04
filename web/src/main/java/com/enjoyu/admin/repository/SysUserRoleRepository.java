package com.enjoyu.admin.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enjoyu.admin.dao.SysUserRoleMapper;
import com.enjoyu.admin.dao.entity.SysRole;
import com.enjoyu.admin.dao.entity.SysUserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleRepository extends ServiceImpl<SysUserRoleMapper, SysUserRole> {

    public List<SysRole> getRoleByUserId(Long userId) {
        return getBaseMapper().queryRoleByUserId(userId);
    }

    public void deleteByUserId(Long userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        remove(wrapper);
    }

    public void deleteByUserIds(List<Long> userIds) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.in("user_id", userIds);
        remove(wrapper);
    }

    public List<Long> getRoleIdsByUserId(Long userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.select("role_id");
        return listObjs(wrapper, o -> Long.parseLong(o.toString()));
    }

    public void deleteByRoleId(String roleId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        remove(wrapper);
    }

    public List<Long> getUserIdsByRoleId(Long roleId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        wrapper.select("user_id");
        return listObjs(wrapper, o -> Long.parseLong(o.toString()));
    }
}
