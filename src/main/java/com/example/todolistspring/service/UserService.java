package com.example.todolistspring.service;

import com.example.todolistspring.domain.User;
import com.example.todolistspring.exception.ResourceNotFoundException;
import com.example.todolistspring.persistance.UserRepository;
import com.example.todolistspring.transfer.userDTO.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        UserService.class
    );

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) {
        LOGGER.info("Create use: {}", request);

        User newUSer = new User();
        newUSer.setFirstName(request.getFirstName());
        newUSer.setLastName(request.getLastName());
        newUSer.setEmail(request.getEmail());
        newUSer.setPassword(request.getPassword());
        // newUSer.setRole(request.getRole());

        return userRepository.save(newUSer);
    }

    public User getUser(Long id) {
        LOGGER.info("Getting user with id: {}", id);

        return userRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "User with id: [" + id + "] not found."
                )
            );
    }
}
