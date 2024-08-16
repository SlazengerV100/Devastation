package com.group3.springwebsocket;

import org.springframework.stereotype.Service;

@Service
public class StateService {

    private final State state = new State();

    public void updateState(int x, int y) {
        state.setX(x);
        state.setY(y);
    }

    public State getState() {
        return state;
    }
}