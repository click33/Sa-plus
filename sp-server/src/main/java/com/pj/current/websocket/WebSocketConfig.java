package com.pj.current.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author ZZ
 */
@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig {
    /**
     * 注入对象ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter getServerEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public MySpringConfigurator getSpringConfigurator() {
        return new MySpringConfigurator();
    }
}
