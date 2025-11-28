package com.springboot.project.techno_shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.springboot.project.techno_shop.enums.Permission.*;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum Role {
    ADMIN(Set.of(
            BRAND_READ, BRAND_WRITE,
            MODEL_READ, MODEL_WRITE,
            COLOR_READ, COLOR_WRITE,
            PRODUCT_READ, PRODUCT_WRITE,
            SALE_READ, SALE_WRITE,
            REPORT_READ
    )),
    SALE(Set.of(
            BRAND_READ,
            MODEL_READ,
            COLOR_READ,
            PRODUCT_READ,
            SALE_READ, SALE_WRITE
    )),
    USER(Set.of(
            BRAND_READ,
            MODEL_READ,
            COLOR_READ,
            PRODUCT_READ,
            SALE_READ
    ));

    private  Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getDescription())).collect(Collectors.toSet());
    }
}
