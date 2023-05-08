package com.example.todolistspring.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolistspring.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

    
}
