package com.enjoyu.admin.component.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Table(name = "smm_role_url")
public class RoleUrl implements Serializable {
    @NotNull
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @NotNull
    @Column(name = "url_id")
    @ApiModelProperty(value = "资源ID")
    private Integer urlId;

    private static final long serialVersionUID = 1L;

}