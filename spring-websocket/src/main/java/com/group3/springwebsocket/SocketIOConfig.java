package com.group3.springwebsocket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class SocketIOConfig {

    private SocketIOServer socketIOServer;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        // Create and start the SocketIOServer
        socketIOServer = new SocketIOServer(config);
        socketIOServer.start();

        return socketIOServer;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        if (socketIOServer != null) {
            socketIOServer.stop();
        }
    }
}
