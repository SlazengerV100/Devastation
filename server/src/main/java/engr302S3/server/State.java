package engr302S3.server;

import engr302S3.server.players.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class State {
    private Map<String, Player> playerMap;
    private final Map<String, Station> stationMap = new HashMap<>();
    private final Tile[][] map;


    public State(Tile[][] map) {
        this.map = map;
    }

    public State(Map<String, Player> playerMap, Tile[][] map) {
        this.playerMap = playerMap;
        this.map = map;
    }
}

