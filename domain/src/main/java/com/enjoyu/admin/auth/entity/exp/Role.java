package com.enjoyu.admin.auth.entity.exp;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table(name = "sys_role")
public class Role extends BaseEntity implements Serializable {

    @Id
    @Column(name = "role_id")
    @NotNull(groups = {Update.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "sys_roles_menus",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "menu_id")})
    private Set<Menu> menus;

    @ManyToMany
    @JoinTable(name = "sys_roles_depts",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "dept_id", referencedColumnName = "dept_id")})
    private Set<Dept> depts;

    @NotBlank
    private String name;

    @Column(name = "level")
    private Integer level = 3;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
