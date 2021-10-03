package com.enjoyu.admin.component.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author enjoyu
 */
@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "smm_user")
public class User implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    @Email
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Column(columnDefinition = "bit(1) default 1")
    @ApiModelProperty(value = "用户状态:0->禁用；1->启用")
    private Boolean status;

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "login_time")
    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    /**
     * JoinTable的name是中间表的名字
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "smm_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    private static final long serialVersionUID = 1L;

}