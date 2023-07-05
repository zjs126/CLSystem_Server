package com.cl.clserverSystem.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class AliOSSProperties {
    private String endpoint="http://oss-cn-beijing.aliyuncs.com";
    private String accessKeyId="xxx";
    private String accessKeySecret="xxx";
    private String bucketName="cl-server-file";
}
