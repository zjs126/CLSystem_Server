package com.cl.clserverSystem.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class AliOSSProperties {
    private String endpoint="http://oss-cn-beijing.aliyuncs.com";
    private String accessKeyId="LTAI5tDaWaG139ab7pVtkWtC";
    private String accessKeySecret="3yKp5WmyMBLW3AcFCHje7rNPUa98Y4";
    private String bucketName="cl-server-file";
}
