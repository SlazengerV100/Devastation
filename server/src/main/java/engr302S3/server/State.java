package engr302S3.server;

import engr302S3.server.players.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class State {
    private Map<String, Player> playerMap;
    private final Map<String, Station> stationMap = new HashMap<>();


    public State(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
    }
}

