package com.example.todolistspring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Role {

    public Role() {
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String userRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", userRole=" + userRole + "]";
    }
    

    
}
