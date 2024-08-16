package com.group3.springwebsocket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class StateController {

    private final SocketIOServer socketIOServer;
    private final StateService stateService;

    public StateController(SocketIOServer socketIOServer, StateService stateService) {
        this.socketIOServer = socketIOServer;
        this.stateService = stateService;
    }

    @PostConstruct
    private void init() {
        // Handle movement commands from clients
        socketIOServer.addEventListener("move", MoveCommand.class, (client, data, ackSender) -> {
            State currentState = stateService.getState();
            int x = currentState.getX();
            int y = currentState.getY();

            switch (data.getDirection()) {
                case "up":
                    if (y > 0) y -= 20;
                    break;
                case "down":
                    if (y < 450) y += 20;
                    break;
                case "left":
                    if (x > 0) x -= 20;
                    break;
                case "right":
                    if (x < 450) x += 20;
                    break;
                default:
                    System.out.println("Unknown direction: " + data.getDirection());
                    return;
            }

            // Update the state
            stateService.updateState(x, y);
            System.out.println("State updated to x=" + x + ", y=" + y);

            // Broadcast the updated state to all connected clients
            socketIOServer.getBroadcastOperations().sendEvent("stateUpdate", stateService.getState());
        });

        // When a client connects, send the current state
        socketIOServer.addConnectListener(client -> {
            System.out.println("Client connected: " + client.getSessionId());
            client.sendEvent("stateUpdate", stateService.getState());
        });
    }
}
