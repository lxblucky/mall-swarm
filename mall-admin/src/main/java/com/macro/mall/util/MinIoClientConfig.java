package com.macro.mall.util;

import com.macro.mall.controller.MinioController;
import io.minio.MinioClient;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description: MinIo的配置类
 * @Author: lss
 * @CreateTime: 2023-02-10 18:01
 * @Version: 1.0
 */
@Data
@Component
public class MinIoClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);
    @Value("${minio.endpoint}")
    private String endpoint;        //MinIO服务所在url
    @Value("${minio.bucketName}")
    private String bucketName;     //存储的桶名称
    @Value("${minio.accessKey}")
    private String accessKey;      //访问的key
    @Value("${minio.secretKey}")
    private String secretKey;      //访问的秘钥

    //注入 MinIo客户端
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey).build();
    }

}
