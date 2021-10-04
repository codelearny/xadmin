package com.enjoyu.admin.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enjoyu.admin.dao.SysMenuMapper;
import com.enjoyu.admin.dao.entity.SysMenu;
import com.enjoyu.admin.dao.entity.SysRoleMenu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysMenuRepository extends ServiceImpl<SysMenuMapper, SysMenu> {

    SysRoleMenuRepository sysRoleMenuRepository;
    SysUserRoleRepository sysUserRoleRepository;

    public List<SysMenu> allMenus() {
        return list();
    }

    public List<SysMenu> getMenusByRoleIds(List<Long> roleIds) {
        List<SysRoleMenu> roleMenus = sysRoleMenuRepository.getByRoleIds(roleIds);
        if (roleMenus.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return listByIds(menuIds);
    }

}
