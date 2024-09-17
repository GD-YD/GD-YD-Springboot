package com.gdyd.gdydapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.gdyd.gdydcore")
@EnableJpaRepositories("com.gdyd.gdydcore")
@EnableFeignClients("com.gdyd.gdydsupport")
@SpringBootApplication(scanBasePackages={"com.gdyd"})
public class GdYdApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GdYdApiApplication.class, args);
    }

}
