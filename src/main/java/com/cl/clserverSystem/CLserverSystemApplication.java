package com.cl.clserverSystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cl.clserverSystem.mapper")
public class CLserverSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CLserverSystemApplication.class, args);
    }

}
