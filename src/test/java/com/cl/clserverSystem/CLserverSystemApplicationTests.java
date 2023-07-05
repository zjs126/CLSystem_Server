package com.cl.clserverSystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class CLserverSystemApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
        String encode =passwordEncoder.encode("abc");
        System.out.println(encode);
        String encdoe1 = passwordEncoder.encode("1234");
        System.out.println(encdoe1);
    }

}
