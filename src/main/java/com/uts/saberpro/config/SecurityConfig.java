package com.uts.saberpro.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("coord")
                .password(passwordEncoder().encode("coordpass"))
                .roles("COORDINACION")
                .build());

        manager.createUser(User.withUsername("estudiante")
                .password(passwordEncoder().encode("estpass"))
                .roles("ESTUDIANTE")
                .build());

        return manager;
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {

            String redirect = "/";

            for (GrantedAuthority ga : authentication.getAuthorities()) {
                String role = ga.getAuthority();

                if (role.equals("ROLE_COORDINACION")) {
                    redirect = "/dashboard";
                    break;
                }

                if (role.equals("ROLE_ESTUDIANTE")) {
                    redirect = "/estudiante";
                    break;
                }
            }

            response.sendRedirect(redirect);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Rutas públicas
                .requestMatchers("/css/**", "/js/**", "/images/**", "/login").permitAll()

                // Rutas de coordinación
                .requestMatchers("/dashboard", "/alumnos/**", "/resultados/**")
                    .hasRole("COORDINACION")

                // Rutas del estudiante
                .requestMatchers("/estudiante", "/estudiante/**")
                    .hasRole("ESTUDIANTE")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler())
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll()
            );

        return http.build();
    }
}
