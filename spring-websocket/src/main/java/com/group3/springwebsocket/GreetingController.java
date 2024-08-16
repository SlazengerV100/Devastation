package com.group3.springwebsocket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class GreetingController {

    private final SocketIOServer socketIOServer;

    public GreetingController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @PostConstruct
    private void init() {
        socketIOServer.addEventListener("message", HelloMessage.class, (client, data, ackSender) -> {
            Greeting greeting = new Greeting("Hello, " + data.getName() + "!");
            socketIOServer.getBroadcastOperations().sendEvent("greeting", greeting);
        });
    }
}