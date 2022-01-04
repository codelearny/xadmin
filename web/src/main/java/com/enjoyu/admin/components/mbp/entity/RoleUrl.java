package com.enjoyu.admin.components.mbp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色后台资源权限关联表
 * </p>
 *
 * @author mbp
 * @since 2022-01-04
 */
@TableName("smm_role_url")
public class RoleUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 资源ID
     */
    private Integer urlId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    @Override
    public String toString() {
        return "RoleUrl{" +
            "roleId=" + roleId +
            ", urlId=" + urlId +
        "}";
    }
}
