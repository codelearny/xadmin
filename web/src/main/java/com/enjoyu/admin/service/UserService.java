package com.enjoyu.admin.service;

import com.enjoyu.admin.dao.entity.SysUser;
import com.enjoyu.admin.repository.SysUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    SysUserRepository sysUserRepository;

    public boolean checkExists(String userName){
        SysUser byName = sysUserRepository.getByName(userName);
        return byName != null;
    }

    public void add(SysUser user) {
        sysUserRepository.addUser(user);
    }
}
