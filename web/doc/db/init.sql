-- 创建数据库 xadmin
CREATE
    DATABASE xadmin;
USE
    xadmin;
-- 系统管理模块 smm
-- 管理员用户
DROP TABLE IF EXISTS `smm_user`;
CREATE TABLE `smm_user`
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(64) UNIQUE NOT NULL COMMENT '用户名',
    password    VARCHAR(64)        NOT NULL COMMENT '密码',
    nickname    VARCHAR(128)       NOT NULL COMMENT '昵称',
    phone       CHAR(11)           NULL COMMENT '手机号',
    mail        VARCHAR(128)       NULL COMMENT '邮件地址',
    salt        VARCHAR(32)        NOT NULL COMMENT '密码盐',
    status      BIT     DEFAULT 1  NOT NULL COMMENT '用户状态:0->禁用；1->启用',
    fail_times  TINYINT DEFAULT 0  NOT NULL COMMENT '失败次数',
    locked_time DATETIME           NULL COMMENT '锁定时间',
    create_time DATETIME           NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '管理员用户表';
-- 管理员角色
DROP TABLE IF EXISTS `smm_role`;
CREATE TABLE `smm_role`
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '角色ID',
    role        VARCHAR(64) UNIQUE NOT NULL COMMENT '角色',
    name        VARCHAR(64)        NOT NULL COMMENT '角色名称',
    description VARCHAR(128)       NOT NULL COMMENT '角色描述',
    create_time DATETIME           NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '管理员角色表';
-- 管理员用户-角色关联表
DROP TABLE IF EXISTS `smm_user_role`;
CREATE TABLE `smm_user_role`
(
    user_id INT UNSIGNED NOT NULL COMMENT '用户ID',
    role_id INT UNSIGNED NOT NULL COMMENT '角色ID',
    UNIQUE KEY idx_user_role (user_id, role_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '管理员用户-角色关联表';
-- 前端资源权限表
DROP TABLE IF EXISTS `smm_menu`;
CREATE TABLE `smm_menu`
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '菜单ID',
    parent_id   INT UNSIGNED      NOT NULL DEFAULT 0 COMMENT '父级ID',
    title       VARCHAR(64)       NOT NULL COMMENT '菜单名称',
    level       TINYINT           NOT NULL COMMENT '菜单级数',
    sort        TINYINT           NOT NULL COMMENT '菜单排序',
    icon        VARCHAR(256)      NOT NULL COMMENT '前端图标',
    description VARCHAR(128)      NOT NULL COMMENT '菜单描述',
    is_deleted  TINYINT DEFAULT 0 NOT NULL COMMENT '是否已删除:0->未删除；1->已删除',
    create_user INT     DEFAULT 0 NOT NULL COMMENT '创建者id',
    create_time DATETIME          NOT NULL COMMENT '创建时间',
    update_user INT     DEFAULT 0 NOT NULL COMMENT '修改者id',
    update_time DATETIME          NOT NULL COMMENT '最新修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '前端资源权限表';
-- 角色前端资源权限关联表
DROP TABLE IF EXISTS `smm_role_menu`;
CREATE TABLE `smm_role_menu`
(
    role_id INT UNSIGNED NOT NULL COMMENT '角色ID',
    menu_id INT UNSIGNED NOT NULL COMMENT '菜单ID',
    UNIQUE KEY idx_role_menu (role_id, menu_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '角色前端资源权限关联表';

-- 后端资源权限表
DROP TABLE IF EXISTS `smm_url`;
CREATE TABLE `smm_url`
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '资源ID',
    name        VARCHAR(64)       NOT NULL COMMENT '资源名称',
    url         VARCHAR(128)      NOT NULL COMMENT '资源URL',
    description VARCHAR(128)      NULL COMMENT '资源描述',
    is_deleted  TINYINT DEFAULT 0 NOT NULL COMMENT '是否已删除:0->未删除；1->已删除',
    create_user INT     DEFAULT 0 NOT NULL COMMENT '创建者id',
    create_time DATETIME          NOT NULL COMMENT '创建时间',
    update_user INT     DEFAULT 0 NOT NULL COMMENT '修改者id',
    update_time DATETIME          NOT NULL COMMENT '最新修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '后端资源权限表';
-- 角色后端资源权限关联表
DROP TABLE IF EXISTS `smm_role_url`;
CREATE TABLE `smm_role_url`
(
    role_id INT UNSIGNED NOT NULL COMMENT '角色ID',
    url_id  INT UNSIGNED NOT NULL COMMENT '资源ID',
    UNIQUE KEY idx_role_url (role_id, url_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '角色后台资源权限关联表';

-- 服务端通用配置字典表
DROP TABLE IF EXISTS `smm_config`;
CREATE TABLE `smm_config`
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '字典ID',
    code        VARCHAR(64) UNIQUE NOT NULL COMMENT '字典编码',
    name        VARCHAR(64)        NOT NULL COMMENT '字典名称',
    value       VARCHAR(64)        NOT NULL COMMENT '字典值',
    description VARCHAR(64)        NOT NULL COMMENT '字典值说明',
    is_deleted  TINYINT DEFAULT 0  NOT NULL COMMENT '是否已删除:0->未删除；1->已删除',
    create_user INT     DEFAULT 0  NOT NULL COMMENT '创建者id',
    create_time DATETIME           NOT NULL COMMENT '创建时间',
    update_user INT     DEFAULT 0  NOT NULL COMMENT '修改者id',
    update_time DATETIME           NOT NULL COMMENT '最新修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '服务端通用配置字典表';

-- 登录日志表
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`
(
    ID          INT UNSIGNED AUTO_INCREMENT,
    USER_ID     INT UNSIGNED NOT NULL COMMENT '登录用户id',
    username    VARCHAR(64)  NOT NULL COMMENT '登录用户名',
    status      TINYINT      NOT NULL COMMENT '登录状态 0 失败 1 成功',
    login_time  DATETIME     NOT NULL COMMENT '登录时间',
    ip          varchar(32)  NULL COMMENT '登录ip',
    description varchar(128) NULL COMMENT '描述',
    primary key (id)
);

