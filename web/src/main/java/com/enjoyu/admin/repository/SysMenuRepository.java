package com.enjoyu.admin.repository;

import com.enjoyu.admin.components.mbp.entity.Menu;
import com.enjoyu.admin.components.mbp.entity.RoleMenu;
import com.enjoyu.admin.components.mbp.service.IMenuService;
import com.enjoyu.admin.components.mbp.service.IRoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysMenuRepository {
    IMenuService menuService;
    IRoleMenuService roleMenuService;

    public List<Menu> allMenus() {
        return menuService.list();
    }

    public List<Menu> getMenusByRoleIds(List<Integer> roleIds) {
        List<RoleMenu> roleMenus = roleMenuService.lambdaQuery().in(RoleMenu::getRoleId, roleIds).list();
        if (roleMenus.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return menuService.listByIds(menuIds);
    }

}
