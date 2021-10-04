package com.enjoyu.admin.shiro.service;

import com.enjoyu.admin.common.exception.ServiceException;
import com.enjoyu.admin.dao.entity.SysMenu;
import com.enjoyu.admin.dao.entity.SysRole;
import com.enjoyu.admin.dao.entity.SysUser;
import com.enjoyu.admin.repository.SysMenuRepository;
import com.enjoyu.admin.repository.SysRoleRepository;
import com.enjoyu.admin.repository.SysUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserAuthService {

    private SysUserRepository sysUserRepository;
    private SysRoleRepository sysRoleRepository;
    private SysMenuRepository sysMenuRepository;

    public SysUser user(String userName) {
        SysUser user = sysUserRepository.getByName(userName);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        return user;
    }

    public List<SysRole> getRolesForUser(SysUser u) {
        return sysRoleRepository.getRolesByUserId(u.getId());
    }

    public Set<String> getPermissions(List<Long> roleIds) {
        List<SysMenu> menusByRoleIds = sysMenuRepository.getMenusByRoleIds(roleIds);
        return menusByRoleIds.stream().map(SysMenu::getUrl).collect(Collectors.toSet());
    }


}
