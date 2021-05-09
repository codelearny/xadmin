-- 创建数据库 xadmin
CREATE DATABASE xadmin;
USE xadmin;
-- 系统管理模块 smm
-- 管理员用户
DROP TABLE IF EXISTS `smm_user`;
CREATE TABLE `smm_user`
(
    id          INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '用户ID',
    username    VARCHAR(64)                 NOT NULL COMMENT '用户名',
    password    VARCHAR(64)                 NOT NULL COMMENT '密码',
    phone       CHAR(11)                                  DEFAULT NULL COMMENT '手机号',
    status      BIT                         NOT NULL DEFAULT 1 COMMENT '用户状态:0->禁用；1->启用',
    create_time DATETIME COMMENT '创建时间',
    login_time  TIMESTAMP COMMENT '最后登录时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '管理员用户表';
-- 管理员角色
DROP TABLE IF EXISTS `smm_role`;
CREATE TABLE `smm_role`
(
    id          INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '角色ID',
    role        VARCHAR(64)                 NOT NULL COMMENT '角色',
    description VARCHAR(128)                NOT NULL COMMENT '角色描述',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '管理员角色表';
-- 管理员用户-角色关联表
DROP TABLE IF EXISTS `smm_user_role`;
CREATE TABLE `smm_user_role`
(
    user_id INT UNSIGNED NOT NULL COMMENT '用户ID',
    role_id INT UNSIGNED NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '管理员用户-角色关联表';
-- 前端资源权限表
DROP TABLE IF EXISTS `smm_menu`;
CREATE TABLE `smm_menu`
(
    id          INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '菜单ID',
    parent_id   INT UNSIGNED DEFAULT 0 COMMENT '父级ID',
    title       VARCHAR(128) COMMENT '菜单名称',
    level       TINYINT UNSIGNED COMMENT '菜单级数',
    sort        TINYINT UNSIGNED COMMENT '菜单排序',
    icon        VARCHAR(256) COMMENT '前端图标',
    description VARCHAR(128)                NOT NULL COMMENT '菜单描述',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '前端资源权限表';
-- 角色前端资源权限关联表
DROP TABLE IF EXISTS `smm_role_menu`;
CREATE TABLE `smm_role_menu`
(
    role_id INT UNSIGNED NOT NULL COMMENT '角色ID',
    menu_id INT UNSIGNED NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '角色前端资源权限关联表';

-- 后端资源权限表
DROP TABLE IF EXISTS `smm_url`;
CREATE TABLE `smm_url`
(
    id          INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '资源ID',
    name        VARCHAR(64)                 NOT NULL COMMENT '资源名称',
    url         VARCHAR(128)                NOT NULL COMMENT '资源URL',
    description VARCHAR(128)                NOT NULL COMMENT '资源描述',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '后端资源权限表';
-- 角色后端资源权限关联表
DROP TABLE IF EXISTS `smm_role_url`;
CREATE TABLE `smm_role_url`
(
    role_id INT UNSIGNED NOT NULL COMMENT '角色ID',
    url_id  INT UNSIGNED NOT NULL COMMENT '资源ID'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '角色后台资源权限关联表';
