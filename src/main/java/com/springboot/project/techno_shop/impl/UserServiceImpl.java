package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.entity.User;
import com.springboot.project.techno_shop.repository.UserRepository;
import com.springboot.project.techno_shop.security.AuthUser;
import com.springboot.project.techno_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User not found"));

        AuthUser authUser = AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .getAuthorities(user.getRole().getGrantedAuthorities())
                .isAccountNonExpired(user.isAccountNonExpired())
                .isAccountNonLocked(user.isAccountNonLocked())
                .isCredentialsNonExpired(user.isCredentialsNonExpired())
                .isEnabled(user.isEnabled())
                .build();
        return Optional.of(authUser);
    }
}
