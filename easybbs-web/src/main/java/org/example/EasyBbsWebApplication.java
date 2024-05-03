package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("org.example.dao")
@EnableTransactionManagement
public class EasyBbsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyBbsWebApplication.class, args);
    }
}
