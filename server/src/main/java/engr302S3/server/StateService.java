package engr302S3.server;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class StateService {
    private Map<String, Player> playerMap = new HashMap<>();

    StateService(){
        playerMap.put("Project Manager", new Player(Player.Role.PROJECT_MANAGER, 0, 50, false));
        playerMap.put("Developer", new Player(Player.Role.DEVELOPER, 100, 50, false));
        playerMap.put("Tester",  new Player(Player.Role.TESTER, 200, 50, false));
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