package engr302S3.server;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class State {
    private Map<String, Player> playerMap;

    public State() {}

    public State(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
    }
}

