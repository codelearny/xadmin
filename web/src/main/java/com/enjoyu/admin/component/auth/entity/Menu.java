package com.enjoyu.admin.component.auth.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "smm_menu")
public class Menu implements Serializable {
    @Id
    @NotNull
    @ApiModelProperty(value = "菜单ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent_id")
    @ApiModelProperty(value = "父级ID")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单级数")
    private Byte level;

    @ApiModelProperty(value = "菜单排序")
    private Byte sort;

    @ApiModelProperty(value = "前端图标")
    private String icon;

    @ApiModelProperty(value = "菜单描述")
    private String description;

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

}