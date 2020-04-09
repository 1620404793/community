package com.lcu.community.commmunity;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lcu.community.commmunity.mapper")
public class CommmunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommmunityApplication.class, args);
    }

}
