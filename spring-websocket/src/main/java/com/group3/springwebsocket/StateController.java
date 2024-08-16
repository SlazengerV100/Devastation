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
        socketIOServer.addEventListener("move", State.class, (client, data, ackSender) -> {
            stateService.updateState(data.getX(), data.getY());
            socketIOServer.getBroadcastOperations().sendEvent("stateUpdate", stateService.getState());
        });

        socketIOServer.addConnectListener(client -> {
            client.sendEvent("stateUpdate", stateService.getState());
        });
    }
}