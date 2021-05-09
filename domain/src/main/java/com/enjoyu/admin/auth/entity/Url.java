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

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "smm_url")
public class Url implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "资源ID")
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "资源名称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "资源描述")
    private String description;

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

}