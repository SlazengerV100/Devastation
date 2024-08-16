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
        // Handle incoming "message" events from clients
        socketIOServer.addEventListener("message", String.class, (client, data, ackSender) -> {
            // Print the received message
            System.out.println("Received message: " + data);

            // Send a response back to the client
            client.sendEvent("response", "Server received your message: " + data);

            // Optionally broadcast the message to all clients
            socketIOServer.getBroadcastOperations().sendEvent("broadcast", "Broadcasting: " + data);
        });
    }
}
