package com.enjoyu.admin.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "smm_role")
public class Role implements Serializable {
    @Id
    @NotNull
    @ApiModelProperty(value = "角色ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "smm_role_menu",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private Set<Menu> menus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "smm_role_url",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "url_id")})
    private Set<Url> urls;


    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

}