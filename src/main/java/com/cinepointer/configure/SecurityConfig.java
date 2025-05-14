package com.cinepointer.configure;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

    private final DataSource datasource;

    public SecurityConfig(DataSource datasource) {
        this.datasource = datasource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
<<<<<<< HEAD
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/signIn", "/signUp", "/users/signup", "/loginError").permitAll()
                .requestMatchers("/guest/**").permitAll()
                .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
=======
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
>>>>>>> branch 'master' of https://github.com/cinepointer/springproject.git
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/signIn")                    // 로그인 폼 경로
                .loginProcessingUrl("/loginCheck")       // 로그인 처리 POST 경로
                .failureUrl("/loginError?error")         // 로그인 실패 시 이동
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("id")
                .passwordParameter("passwd")
                .permitAll()
                //.disable()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signIn")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
        manager.setUsersByUsernameQuery("select id, passwd, enabled from member where id=?");
        manager.setAuthoritiesByUsernameQuery("select id, authority from member where id=?");
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/loginError?error");
    }
}
