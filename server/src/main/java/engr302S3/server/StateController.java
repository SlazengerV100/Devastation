package engr302S3.server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class StateController {

    @Autowired
    private StateService stateService;

    private Map<String, String> playerIdMap = new HashMap<>();
    private AtomicInteger playerNumber = new AtomicInteger(1); // Counter for player numbers

    @MessageMapping("/setState")
    @SendTo("/topic/state")
    public State setState(@Header("simpSessionId") String sessionId, State request) {
        // Check if sessionId is not in the map
        if (!playerIdMap.containsKey(sessionId)) {
            // Assign a new player name based on the current number
            String playerName = "player" + playerNumber.getAndIncrement();
            playerIdMap.put(sessionId, playerName);
        }

        System.out.println("Position update by: " + playerIdMap.get(sessionId));
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

