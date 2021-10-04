package com.enjoyu.admin.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enjoyu.admin.dao.SysUserMapper;
import com.enjoyu.admin.dao.entity.SysUser;
import org.springframework.stereotype.Service;

@Service
public class SysUserRepository extends ServiceImpl<SysUserMapper, SysUser> {

    public void deleteUser(Long userId) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        wrapper.set("state", 2);
        wrapper.eq("id", userId);
        update(wrapper);
    }

    public SysUser getByName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        return getOne(wrapper);
    }

    public Long addUser(SysUser sysUser) {
        this.save(sysUser);
        return sysUser.getId();
    }

    public void updateUser(SysUser sysUser) {
        updateById(sysUser);
    }

}
