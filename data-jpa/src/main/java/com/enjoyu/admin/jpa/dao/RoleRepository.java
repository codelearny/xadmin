package com.enjoyu.admin.jpa.dao;

import com.enjoyu.admin.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findFirstByRole(String role);
}
