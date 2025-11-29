package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.enums.Role;
import com.springboot.project.techno_shop.security.AuthUser;
import com.springboot.project.techno_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceFakeImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        List<AuthUser> users = List.of(
                new AuthUser(Role.ADMIN.getGrantedAuthorities(), passwordEncoder.encode("admin1234"), "admin1", true, true, true, true),
                new AuthUser(Role.SALE.getGrantedAuthorities(), passwordEncoder.encode("sale1234"), "sale1", true, true, true, true)
        );
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
