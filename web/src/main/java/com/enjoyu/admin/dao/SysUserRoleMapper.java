package com.enjoyu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enjoyu.admin.dao.entity.SysRole;
import com.enjoyu.admin.dao.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<SysRole> queryRoleByUserId(Long userId);
}
