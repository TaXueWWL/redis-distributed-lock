package com.snowalker;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AlipayGatewayServerBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayGatewayServerBootstrap.class);

    public static void main(String[] args) {
        SpringApplication.run(AlipayGatewayServerBootstrap.class, args);
        LOGGER.info("redis-distributed-lock-demo-spring启动完成......");
    }

    /**
     * @author snowalker
     * @date 2017-3-17
     * @describe 优化tomcat线程数目
     */
    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol = (Http11NioProtocol) connector
                    .getProtocolHandler();
            // 设置最大连接数
            protocol.setMaxConnections(2000);
            // 设置最大线程数
            protocol.setMaxThreads(2000);
            protocol.setConnectionTimeout(30000);
        }
    }
}
