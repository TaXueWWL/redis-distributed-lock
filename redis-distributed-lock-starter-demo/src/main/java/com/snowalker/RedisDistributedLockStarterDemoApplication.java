package com.snowalker;

import com.snowalker.executor.rmqtest.MyRMQReveiver;
import com.snowalker.lock.redisson.config.EnableRedissonLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRedissonLock
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.snowalker", "com.gyjx.marketing"})
public class RedisDistributedLockStarterDemoApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context =
				SpringApplication.run(RedisDistributedLockStarterDemoApplication.class, args);
//		ConfigTest configTest = (ConfigTest) context.getBean("configTest");
//		MyRMQSender sender = context.getBean(MyRMQSender.class);
//		sender.send();
	}


}
