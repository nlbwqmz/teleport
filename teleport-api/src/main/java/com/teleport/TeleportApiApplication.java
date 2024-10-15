package com.teleport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TeleportApiApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TeleportApiApplication.class);
        springApplication.setHeadless(false);
        springApplication.run(args);
    }
}
