package com.enjoyu.admin.common.validate;

import com.enjoyu.admin.common.validate.annotation.PhoneNumber;

import javax.validation.constraints.Email;

public class User {
    private int id;
    private String name;
    @PhoneNumber
    private String phone;
    @Email
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
