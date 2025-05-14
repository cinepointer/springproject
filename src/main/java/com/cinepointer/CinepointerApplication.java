package com.cinepointer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@MapperScan("com.cinepointer.dao")
public class CinepointerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinepointerApplication.class, args);
    }
}
