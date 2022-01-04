package com.enjoyu.admin.components.mbp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.shiro.ShiroUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

public class MyMetaObjHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        User principal = ShiroUtil.currentUser();
        strictInsertFill(metaObject, "createUser", Integer.class, principal.getId());
        strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User principal = ShiroUtil.currentUser();
        strictUpdateFill(metaObject, "updateUser", Integer.class, principal.getId());
        strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
