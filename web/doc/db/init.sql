create table sys_user
(
    ID           bigint unsigned auto_increment comment 'ID'
        primary key,
    USER_NAME    varchar(40)                    not null comment '用户名',
    PASSWORD     varchar(50)                    not null comment '密码',
    SALT         varchar(45)                    not null comment 'md5密码盐',
    MAIL         varchar(100)        default '' null comment '邮件地址',
    MOBILE_PHONE varchar(30)         default '' null comment '移动电话',
    STATE        tinyint(1) unsigned default 1  not null comment '1 正常 0 删除',
    CREATE_TIME  datetime                       not null comment '创建时间',
    LOCK_TIME    datetime                       null comment '锁定日期'
);

create table sys_role_user
(
    ID          bigint auto_increment comment 'ID'
        primary key,
    ROLE_ID     bigint                             not null comment '角色ID',
    USER_ID     bigint                             not null comment '用户ID',
    USER_NAME   varchar(32)                        not null comment '用户名',
    CREATE_TIME datetime default CURRENT_TIMESTAMP null comment '创建时间'
);

create table sys_role
(
    ID          bigint unsigned auto_increment comment 'ID'
        primary key,
    ROLE_NAME   varchar(80)                            not null comment '角色名称',
    DESCRIPTION varchar(255) default ''                null comment '描述',
    CREATE_TIME datetime     default CURRENT_TIMESTAMP null comment '创建时间'
);

create table sys_menu
(
    ID          bigint unsigned auto_increment comment 'ID'
        primary key,
    MENU_NAME   varchar(128)                                  not null comment '菜单名称',
    DESCRIPTION varchar(228)        default ''                null comment '描述',
    LEVEL       tinyint(1)                                    not null comment '菜单级别',
    PARENT_ID   bigint                                        not null comment '上级菜单ID',
    ICON        varchar(64)         default ''                null comment '菜单图标',
    URL         varchar(500)                                  not null comment 'URL',
    STATUS      tinyint(1) unsigned default 1                 not null comment '状态 0 删除 1 正常',
    CREATE_TIME datetime            default CURRENT_TIMESTAMP null comment '创建时间'
);

create table sys_role_menu
(
    ID          bigint unsigned auto_increment comment 'ID'
        primary key,
    MENU_ID     bigint                                        not null comment '菜单ID',
    MENU_NAME   varchar(128)        default ''                null comment '菜单名称',
    ROLE_ID     bigint                                        not null comment '角色ID',
    ROLE_NAME   varchar(128)        default ''                null comment '角色名称',
    STATE       tinyint(1) unsigned default 1                 not null comment '状态 0 删除，1 正常',
    CREATE_TIME datetime            default CURRENT_TIMESTAMP null comment '创建时间'
);

create table sys_login_log
(
    ID          bigint unsigned auto_increment
        primary key,
    USER_ID     bigint       null comment '登录用户id',
    USER_NAME   varchar(50)  null comment '登录用户名',
    STATUS      tinyint(3)   null comment '登录状态 0 失败 1 成功',
    LOGIN_TIME  datetime     null comment '登录时间',
    IP          varchar(32)  null comment '登录ip',
    DESCRIPTION varchar(100) null comment '描述'
);

