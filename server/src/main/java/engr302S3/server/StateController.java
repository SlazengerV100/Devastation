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
    @MessageMapping("/player/move")
    @SendTo("/topic/player/move")
    public State movePlayer(Movement movementRequest) {
        // Update game state
        stateService.movePlayer(movementRequest.getRole(), movementRequest.getDirection());

        //Send back to client
        return new State(stateService.getPlayerMap());
    }

    @MessageMapping("/player/activate")
    @SendTo("/topic/player/activate")
    public State activatePlayer(Activation activationRequest){
        // Update game state
        if (activationRequest.isActivate()){
            stateService.activatePlayer(activationRequest.getPlayerTitle());
        } else {
            stateService.deactivatePlayer(activationRequest.getPlayerTitle());
        }

        //Send back to client
        return new State(stateService.getPlayerMap());
    }

    @MessageMapping("/player/ticket/pickUp")
    @SendTo("/topic/player/ticket/pickUp")
    public State pickUpTicket() {
        return new State(stateService.getPlayerMap());
    }

    @MessageMapping("/player/ticket/drop")
    @SendTo("/topic/player/ticket/drop")
    public State dropTicket() {
        return new State(stateService.getPlayerMap());
    }

    @SendTo("/topic/player/burnOut")
    public void broadcastPlayerBurnOut() {

    }

    @SendTo("/topic/player/revive")
    public void broadcastPlayerRevive() {

    }

    @MessageMapping("/players")
    @SendTo("/topic/players")
    public State getState() {
        return new State(stateService.getPlayerMap());
    }

    @SendTo("/topic/scoreUpdate")
    public void broadcastScoreUpdate() {

    }

    @SendTo("/topic/ticket/create")
    public void broadcastTicketCreate() {

    }

    @SendTo("/topic/ticket/resolve")
    public void broadcastTicketResolve() {

    }

    @MessageMapping("/tickets")
    @SendTo("/topic/tickets")
    public State getTickets() {
        return null;
    }

    @MessageMapping("/stations")
    @SendTo("/topic/stations")
    public State getStations() {
        return null;
    }

    @MessageMapping("/tiles")
    @SendTo("/topic/tiles")
    public State getTiles() {
        return null;
    }
}
