package engr302S3.server;

import engr302S3.server.devastation.Devastation;
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
    private Devastation game;
    private boolean gameStarted = false;

    StateService(){
        playerMap.put("Project Manager", new ProjectManager(0, 50, false));
        playerMap.put("Developer", new Developer(100, 50, false));
        playerMap.put("Tester",  new Tester(200, 50, false));

        this.game = new Devastation(10,10);
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
        // Start the game if it's the first player activation and the game hasn't started
        if (!gameStarted) {
            gameStarted = true;  // Mark that the game has started
            game.runGame();
        }
    }

    public void deactivatePlayer(String playerTitle){
        if (!playerMap.containsKey(playerTitle)){
            return;
        }
        Player player = playerMap.get(playerTitle);
        player.setActive(false);
    }


}