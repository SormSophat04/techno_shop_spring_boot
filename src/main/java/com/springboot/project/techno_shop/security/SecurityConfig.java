package com.springboot.project.techno_shop.security;

import com.springboot.project.techno_shop.enums.Permission;
import com.springboot.project.techno_shop.enums.Role;
import com.springboot.project.techno_shop.jwt.JwtFilter;
import com.springboot.project.techno_shop.jwt.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new LoginFilter(authenticationManager))
                .addFilterAfter(new JwtFilter(), LoginFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        // Brand
                        .requestMatchers(HttpMethod.GET, "/brands/**").hasAuthority(Permission.BRAND_READ.getDescription())
                        .requestMatchers(HttpMethod.POST, "/brands").hasAuthority(Permission.BRAND_WRITE.getDescription())
                        .requestMatchers(HttpMethod.PUT, "/brands/**").hasAuthority(Permission.BRAND_WRITE.getDescription())
                        .requestMatchers(HttpMethod.DELETE, "/brands/**").hasAuthority(Permission.BRAND_WRITE.getDescription())

                        // Model
                        .requestMatchers(HttpMethod.GET, "/models/**").hasAuthority(Permission.MODEL_READ.getDescription())
                        .requestMatchers(HttpMethod.POST, "/models").hasAuthority(Permission.MODEL_WRITE.getDescription())
                        .requestMatchers(HttpMethod.PUT, "/models/**").hasAuthority(Permission.MODEL_WRITE.getDescription())
                        .requestMatchers(HttpMethod.DELETE, "/models/**").hasAuthority(Permission.MODEL_WRITE.getDescription())

                        // Color
                        .requestMatchers(HttpMethod.GET, "/colors/**").hasAuthority(Permission.COLOR_READ.getDescription())
                        .requestMatchers(HttpMethod.POST, "/colors").hasAuthority(Permission.COLOR_WRITE.getDescription())
                        .requestMatchers(HttpMethod.PUT, "/colors/**").hasAuthority(Permission.COLOR_WRITE.getDescription())
                        .requestMatchers(HttpMethod.DELETE, "/colors/**").hasAuthority(Permission.COLOR_WRITE.getDescription())

                        // Product
                        .requestMatchers(HttpMethod.GET, "/products/**").hasAuthority(Permission.PRODUCT_READ.getDescription())
                        .requestMatchers(HttpMethod.POST, "/products").hasAuthority(Permission.PRODUCT_WRITE.getDescription())

                        // Sale
                        .requestMatchers(HttpMethod.GET, "/sales/**").hasAuthority(Permission.SALE_READ.getDescription())
                        .requestMatchers(HttpMethod.POST, "/sales").hasAuthority(Permission.SALE_WRITE.getDescription())
                        .requestMatchers(HttpMethod.PUT, "/sales/**").hasAuthority(Permission.SALE_WRITE.getDescription())

                        // Report
                        .requestMatchers(HttpMethod.GET, "/report/**").hasAuthority(Permission.REPORT_READ.getDescription())

                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .authorities(Role.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails sale = User.builder()
                .username("sale")
                .password(passwordEncoder.encode("sale123"))
                .authorities(Role.SALE.getGrantedAuthorities())
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .authorities(Role.USER.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, sale, user);
    }
}
