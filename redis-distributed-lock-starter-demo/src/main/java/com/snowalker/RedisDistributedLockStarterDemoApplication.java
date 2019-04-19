package com.snowalker;

import com.snowalker.lock.redisson.config.EnableRedissonLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRedissonLock
@EnableScheduling
@SpringBootApplication
public class RedisDistributedLockStarterDemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RedisDistributedLockStarterDemoApplication.class, args);
	}


}
