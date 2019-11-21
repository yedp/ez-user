package com.ydp.ez.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.ydp.ez.user.mapper")
@SpringBootApplication
public class EzUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(EzUserApplication.class, args);
	}

}
