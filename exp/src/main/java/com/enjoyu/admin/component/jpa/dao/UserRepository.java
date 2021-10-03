package com.enjoyu.admin.component.jpa.dao;

import com.enjoyu.admin.component.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author enjoyu
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 根据用户名查找用户
     *
     * @param username 用户名称
     * @return 用户DO
     */
    User findFirstByUsername(String username);
}
