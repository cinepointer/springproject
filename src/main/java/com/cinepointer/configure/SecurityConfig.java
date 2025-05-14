package com.cinepointer.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
public class SecurityConfig {
	@Autowired
	private DataSource datasource;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
    // 1. SecurityFilterChain 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // CSRF 비활성화
            .cors(cors -> cors.disable())   // CORS 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/loginForm", "/signin", "/signup", "/css/**", "/js/**", "/img/**").permitAll() // 공개 URL
                .requestMatchers("/guest/**").permitAll()  // 게스트 전용
                .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")  // ROLE_USER 또는 ROLE_ADMIN 권한
                .requestMatchers("/admin/**").hasRole("ADMIN")  // ROLE_ADMIN 권한만
                .anyRequest().authenticated()  // 그 외는 인증 필요
            )
            .formLogin(form -> form
                .loginPage("/signin")  // 커스텀 로그인 페이지
                .loginProcessingUrl("/users/signin")  // 로그인 처리 URL
                .defaultSuccessUrl("/login", true)  // 로그인 성공 후 이동 URL
                .failureHandler(authenticationFailureHandler)
                .permitAll()  // 모든 사용자에게 로그인 페이지 허용
            )
            .logout(logout -> logout
                .logoutUrl("/signout")  // 로그아웃 URL
                .logoutSuccessUrl("/logout")  // 로그아웃 성공 후 이동 URL
                .permitAll()  // 로그아웃은 모든 사용자 허용
            );
        
        return http.build();
    }

    // 2. PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화 방식
    }

//    // 3. UserDetailsService Bean 등록
//    //비활성화됨
//    @Bean
//    public UserDetailsService userDetailsService(userServiceImpl userService) {
//        return userService;  // userServiceImpl을 UserDetailsService로 사용
//    }

    // 4. AuthenticationManager Bean 등록 (JDBC 인증 처리)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder
            .jdbcAuthentication()
            .dataSource(datasource)  // 데이터 소스 설정
            .usersByUsernameQuery("select user_id, user_passwd,user_enabled from users where user_id=?")
            .authoritiesByUsernameQuery("select user_id,role_name from users where user_id=?")
            .passwordEncoder(passwordEncoder());  // 암호화 방식 설정
        
        return authenticationManagerBuilder.build();
    }
    

    

}
