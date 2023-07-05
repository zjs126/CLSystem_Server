package com.cl.clserverSystem.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@Component
public class AliOSSUtils {

    @Autowired
    private AliOSSProperties aliOSSProperties;

    public String upload(MultipartFile file) throws Exception {

        String endpoint =aliOSSProperties.getEndpoint();
        String accessKeyId=aliOSSProperties.getAccessKeyId();
        String accessKeySecret=aliOSSProperties.getAccessKeySecret();
        String bucketName=aliOSSProperties.getBucketName();

        InputStream inputStream = file.getInputStream();

        String fileName = UUID.randomUUID().toString()+".png";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        ossClient.shutdown();
        return url;
    }
}