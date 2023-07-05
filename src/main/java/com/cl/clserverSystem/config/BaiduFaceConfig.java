package com.cl.clserverSystem.config;


import com.baidu.aip.face.AipFace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaiduFaceConfig {
    @Value(("${face.baidu.appid}"))
    private String appId;
    @Value(("${face.baidu.key}"))
    private String key;
    @Value(("${face.baidu.secret}"))
    private String secret;

    @Bean
    public AipFace AipFace() {
        //System.out.println("人脸配置："+appId+"=="+key+"=="+secret);
        return new AipFace(appId, key, secret);
    }
}

