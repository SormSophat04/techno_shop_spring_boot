package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.UserDTO;
import com.springboot.project.techno_shop.entity.Role;
import com.springboot.project.techno_shop.entity.User;
import com.springboot.project.techno_shop.repository.RoleRepository;
import com.springboot.project.techno_shop.repository.UserRepository;
import com.springboot.project.techno_shop.security.AuthUser;
import com.springboot.project.techno_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User not found"));

        AuthUser authUser = AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .getAuthorities(grantedAuthorities(user.getRoles()))
                .isAccountNonExpired(user.isAccountNonExpired())
                .isAccountNonLocked(user.isAccountNonLocked())
                .isCredentialsNonExpired(user.isCredentialsNonExpired())
                .isEnabled(user.isEnabled())
                .build();
        return Optional.of(authUser);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
//        user.setRoles(userDTO.getRole());

        Role userRole = roleRepository.findByName("Admin").orElseThrow(() -> new IllegalStateException("Role USER not found"));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
    }

    private Set<SimpleGrantedAuthority> grantedAuthorities(Set<Role> roles){
        Set<SimpleGrantedAuthority> authoritiesRole = roles.stream()
                .map(role -> new SimpleGrantedAuthority(STR."ROLE_\{role.getName()}"))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .flatMap(this::toStream)
                .collect(Collectors.toSet());

        authorities.addAll(authoritiesRole);
        return authorities;
    }

    private Stream<SimpleGrantedAuthority> toStream(Role role){
        return role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()));
    }
}
