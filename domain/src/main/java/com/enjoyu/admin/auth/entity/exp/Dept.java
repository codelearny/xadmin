package com.enjoyu.admin.auth.entity.exp;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author enjoyu
 */
@Getter
@Setter
@Table(name = "sys_dept")
public class Dept extends BaseEntity implements Serializable {

    @Id
    @Column(name = "dept_id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

    @OneToMany(mappedBy = "dept", orphanRemoval = false)
    private Set<User> users;

    private Integer deptSort;

    @NotBlank
    private String name;

    @NotNull
    private Boolean enabled;

    private Long pid;

    private Integer subCount = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dept dept = (Dept) o;
        return Objects.equals(id, dept.id) &&
                Objects.equals(name, dept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}