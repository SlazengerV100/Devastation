package engr302S3.server;

import engr302S3.server.players.Developer;
import engr302S3.server.players.Player;
import engr302S3.server.players.ProjectManager;
import engr302S3.server.players.Tester;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class StateService {
    private final Map<String, Player> playerMap = new HashMap<>();
    private final Map<String, Station> stationMap = new HashMap<>();
    private final Tile[][] map;

    StateService(){
        playerMap.put("PM", new ProjectManager(false));
        playerMap.put("DV", new Developer(false));
        playerMap.put("TS",  new Tester(false));

        stationMap.put("FRE", new Station(StationType.FRONTEND));
        stationMap.put("BKE", new Station(StationType.BACKEND));
        stationMap.put("API", new Station(StationType.API));

        stationMap.put("STA", new Station(StationType.STATIC_ANALYSIS));
        stationMap.put("COV", new Station(StationType.COVERAGE_TESTING));
        stationMap.put("UNI", new Station(StationType.UNIT_TESTING));


        this.map = MapLoader.loadMap( playerMap, stationMap);
    }

    public void movePlayer(String playerTitle, String direction) {
        if (!playerMap.containsKey(playerTitle)) {
            return;
        }

        Player player = playerMap.get(playerTitle);
        int newX = player.getX();
        int newY = player.getY();

        switch (direction.toUpperCase()) {
            case "UP" -> newY -= 20; // Move up decreases the y-coordinate by 20
            case "DOWN" -> newY += 20; // Move down increases the y-coordinate by 20
            case "LEFT" -> newX -= 20; // Move left decreases the x-coordinate by 20
            case "RIGHT" -> newX += 20; // Move right increases the x-coordinate by 20
            default -> {
                return; // If the direction is not valid, do nothing
            }
        }

        player.setX(newX);
        player.setY(newY);
    }


    public void activatePlayer(String playerTitle){
        if (!playerMap.containsKey(playerTitle)){
            return;
        }
        Player player = playerMap.get(playerTitle);
        player.setActive(true);
    }

    public void deactivatePlayer(String playerTitle){
        if (!playerMap.containsKey(playerTitle)){
            return;
        }
        Player player = playerMap.get(playerTitle);
        player.setActive(false);
    }


}