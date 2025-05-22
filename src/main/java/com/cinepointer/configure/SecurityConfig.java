package com.cinepointer.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource datasource;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
        manager.setUsersByUsernameQuery("select user_id as username, user_passwd as password, user_enabled as enabled from users where user_id=?");
        manager.setAuthoritiesByUsernameQuery("select user_id as username, role_name as authority from users where user_id=?");
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // AuthenticationManagerBuilder 직접 가져오기
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // UserDetailsService, PasswordEncoder 연결
        authBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authBuilder.build();

        http
            .authenticationManager(authenticationManager)  // filterChain에 AuthenticationManager 등록
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/loginForm", "/signin", "/signup", "/css/**", "/js/**", "/img/**", "users/signup").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/signin")
                .loginProcessingUrl("/users/signin")
                .defaultSuccessUrl("/login", true)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                    .loginPage("/signin") // 커스텀 로그인 페이지
                    .defaultSuccessUrl("/loginSuccess", true)
                )
            .logout(logout -> logout
                .logoutUrl("/signout")
                .logoutSuccessUrl("/logout")
                .permitAll()
            );

        return http.build();
    }
}
