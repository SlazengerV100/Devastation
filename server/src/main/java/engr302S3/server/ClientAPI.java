package engr302S3.server;

import engr302S3.server.map.Board;
import engr302S3.server.map.Station;
import engr302S3.server.map.Tile;
import engr302S3.server.playerActions.Activation;
import engr302S3.server.playerActions.Movement;

import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ClientAPI {
    private Devastation devastation;

    @Autowired
    ClientAPI(Devastation devastation) {
        this.devastation = devastation;
    }

    @MessageMapping("/player/move")
    @SendTo("/topic/player/move")
    public Player movePlayer(Movement movementRequest) {
        return null;
    }

    @MessageMapping("/player/activate")
    @SendTo("/topic/player/activate")
    public Player activatePlayer(Activation activationRequest){
        return null;
    }

    @MessageMapping("/player/ticket/pickUp")
    @SendTo("/topic/player/ticket/pickUp")
    public Player pickUpTicket() {
        return null;
    }

    @MessageMapping("/player/ticket/drop")
    @SendTo("/topic/player/ticket/drop")
    public Player dropTicket() {
        return null;
    }

    @SendTo("/topic/player/burnOut")
    public Player broadcastPlayerBurnOut() {
        return null;
    }

    @SendTo("/topic/player/revive")
    public Player broadcastPlayerRevive() {
        return null;
    }

    @MessageMapping("/players")
    @SendTo("/topic/players")
    public Player[] getPlayers() {
        return null;
    }

    @SendTo("/topic/scoreUpdate")
    public Board broadcastScoreUpdate() {
        return null;
    }

    @SendTo("/topic/ticket/create")
    public Ticket broadcastTicketCreate() {
        return null;
    }

    @SendTo("/topic/ticket/resolve")
    public Ticket broadcastTicketResolve() {
        return null;
    }

    @MessageMapping("/tickets")
    @SendTo("/topic/tickets")
    public Ticket[] getTickets() {
        return null;
    }

    @MessageMapping("/stations")
    @SendTo("/topic/stations")
    public Station[] getStations() {
        return null;
    }

    @MessageMapping("/tiles")
    @SendTo("/topic/tiles")
    public Tile[] getTiles() {
        return null;
    }
}
