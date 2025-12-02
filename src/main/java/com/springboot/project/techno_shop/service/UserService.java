package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.UserDTO;
import com.springboot.project.techno_shop.security.AuthUser;

import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findUserByUsername(String username);

    void createUser(UserDTO userDTO);
}
