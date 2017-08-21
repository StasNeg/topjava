package ru.javawebinar.topjava.repository.jdbc;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Stanislav on 21.08.2017.
 */

@Table(name = "user_roles")
public class RoleUser {
    private Integer user_id;
    private String role;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RoleUser() {

    }

    public RoleUser(Integer user_id, String role) {
        this.user_id = user_id;
        this.role = role;
    }
}
