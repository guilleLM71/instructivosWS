package com.example.ms_instructivos.infrastructure.config;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MinioConfig {

    @Bean
    public MinioClient initMinioClient() {
        return MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("13zMCUmtVWaCk4JqyIdC",  "Gp0OxGhoXTXzlO6AUXu8oQjQ1s4zLt77Tyhbme6k")
                .build();
    }


}