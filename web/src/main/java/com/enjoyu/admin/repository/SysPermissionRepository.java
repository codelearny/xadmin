
package com.enjoyu.admin.repository;

import com.enjoyu.admin.components.mbp.entity.RoleUrl;
import com.enjoyu.admin.components.mbp.entity.Url;
import com.enjoyu.admin.components.mbp.service.IRoleUrlService;
import com.enjoyu.admin.components.mbp.service.IUrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysPermissionRepository {
    IUrlService urlService;
    IRoleUrlService roleUrlService;

    public List<Url> allPermissions() {
        return urlService.list();
    }

    public List<Url> getPermissionsByRoleIds(List<Integer> roleIds) {
        List<RoleUrl> roleUrls = roleUrlService.lambdaQuery().in(RoleUrl::getRoleId, roleIds).list();
        if (roleUrls.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> collect = roleUrls.stream().map(RoleUrl::getUrlId).collect(Collectors.toList());
        return urlService.listByIds(collect);
    }

}
