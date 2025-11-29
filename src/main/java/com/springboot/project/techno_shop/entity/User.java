package com.springboot.project.techno_shop.entity;

import com.springboot.project.techno_shop.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private  String lastName;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

}
