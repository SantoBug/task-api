package com.douglas.api_cach_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class ApiCachRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCachRedisApplication.class, args);
	}

}
