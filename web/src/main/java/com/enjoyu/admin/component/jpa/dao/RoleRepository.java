package com.enjoyu.admin.component.jpa.dao;

import com.enjoyu.admin.component.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author enjoyu
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * 根据角色名查找角色
     *
     * @param role 角色名称
     * @return 角色DO
     */
    Role findFirstByRole(String role);
}
