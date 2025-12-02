package com.springboot.project.techno_shop.dto;

import com.springboot.project.techno_shop.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String password;
//    private Role role;
}
