package com.enjoyu.admin.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@Entity
@Data
@Table(name = "smm_role_menu")
public class RoleMenu implements Serializable {
    @NotNull
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @NotNull
    @Column(name = "menu_id")
    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    private static final long serialVersionUID = 1L;

}