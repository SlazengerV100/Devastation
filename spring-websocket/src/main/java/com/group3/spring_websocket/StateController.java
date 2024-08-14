package com.group3.spring_websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StateController {

    @Autowired
    private StateService stateService;

    @MessageMapping("/setState")
    @SendTo("/topic/state")
    public State setState(State request) {
        stateService.setX(request.getX());
        stateService.setY(request.getY());
        return new State(stateService.getX(), stateService.getY());
    }

    @MessageMapping("/getState")
    @SendTo("/topic/state")
    public State getState() {
        return new State(stateService.getX(), stateService.getY());
    }
}
