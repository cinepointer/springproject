package com.cinepointer.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // 추가!
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 꼭 필요!
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/users/signin",
                    "/users/signup",
                    "/",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                .anyRequest()
                .permitAll()
                //.authenticated()
            )
            .formLogin(form -> form
                .loginPage("/users/signin")
                .loginProcessingUrl("/users/signin")
                .defaultSuccessUrl("/mainpage", true)
                .permitAll()
                //.disable()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }

    // PasswordEncoder Bean 등록!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
