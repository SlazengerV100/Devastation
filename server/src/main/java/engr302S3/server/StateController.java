package engr302S3.server;
import engr302S3.server.playerActions.Activation;
import engr302S3.server.playerActions.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StateController {

    @Autowired
    private StateService stateService;

    @MessageMapping("/movePlayer")
    @SendTo("/topic/state")
    public State movePlayer(Movement movementRequest) {
        // Update game state with direction and state
        stateService.movePlayer(
                movementRequest.getPlayerTitle(),
                movementRequest.getDirection()
        );

        // Send back to client with updated state
        return new State(stateService.getPlayerMap());
    }

    @MessageMapping("/activatePlayer")
    @SendTo("/topic/state")
    public State activatePlayer(Activation activationRequest){
        // Update game state
        if (activationRequest.isActivate()){
            stateService.activatePlayer(activationRequest.getPlayerTitle());
        }
        else{
            stateService.deactivatePlayer(activationRequest.getPlayerTitle());
        }

        //Send back to client
        return new State(stateService.getPlayerMap());

    }

    @MessageMapping("/getState")
    @SendTo("/topic/state")
    public State getState() {
        return new State(stateService.getPlayerMap());
    }

}

