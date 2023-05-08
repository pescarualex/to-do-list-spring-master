package com.example.todolistspring.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolistspring.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
