package com.enjoyu.admin.component.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@Entity
@Data
@Table(name = "smm_user_role")
public class UserRole implements Serializable {
    @NotNull
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @NotNull
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    private static final long serialVersionUID = 1L;

}