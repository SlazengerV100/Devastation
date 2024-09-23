package engr302S3.server;

import engr302S3.server.map.Position;
import engr302S3.server.map.Board;
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

    StateService() {
        playerMap.put("Project Manager", new ProjectManager(new Position(Board.BOARD_WIDTH/4, Board.BOARD_HEIGHT/2)));
        playerMap.put("Developer", new Developer(new Position(Board.BOARD_WIDTH/2, Board.BOARD_HEIGHT/2)));
        playerMap.put("Tester", new Tester(new Position((Board.BOARD_WIDTH/4) * 3, Board.BOARD_HEIGHT/2)));
    }

    public void movePlayer(String role, String direction) {
        if (!playerMap.containsKey(role)) {
            return;
        }

        playerMap.get(role).movePlayer(Player.Direction.valueOf(direction.toUpperCase()));
    }

    public void activatePlayer(String playerTitle) {
        if (!playerMap.containsKey(playerTitle)) {
            return;
        }

        playerMap.get(playerTitle).setActive(true);
    }

    public void deactivatePlayer(String playerTitle) {
        if (!playerMap.containsKey(playerTitle)) {
            return;
        }

        playerMap.get(playerTitle).setActive(false);
    }
}