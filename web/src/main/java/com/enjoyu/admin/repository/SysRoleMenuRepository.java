package com.enjoyu.admin.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enjoyu.admin.dao.SysRoleMenuMapper;
import com.enjoyu.admin.dao.entity.SysRoleMenu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysRoleMenuRepository extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> {

    public List<Long> getMenuIdsByRoleId(Long roleId) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        wrapper.select("menu_id");
        return listObjs(wrapper, o -> Long.parseLong(o.toString()));
    }

    public void deleteByRoleIds(List<Long> roleIds) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.in("role_id", roleIds);
        remove(wrapper);
    }

    public void deleteByRoleId(Long roleId) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        remove(wrapper);
    }

    public List<SysRoleMenu> getByRoleIds(List<Long> roleIds) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.in("role_id", roleIds);
        wrapper.orderByDesc("create_time");
        return list(wrapper);
    }

}
