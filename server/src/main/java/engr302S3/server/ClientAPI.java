package engr302S3.server;

import engr302S3.server.map.Station;
import engr302S3.server.map.Tile;
import engr302S3.server.playerActions.Activation;
import engr302S3.server.playerActions.Movement;

import engr302S3.server.playerActions.PlayerRequest;
import engr302S3.server.playerActions.TaskProgressBroadcast;
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
        Player player = devastation.getBoard().getPlayers().get(movementRequest.playerId());
        player.movePlayer(movementRequest.direction());
        return player;
    }

    @MessageMapping("/player/activate")
    @SendTo("/topic/player/activate")
    public Player activatePlayer(Activation activationRequest){
        Player player = devastation.getBoard().getPlayers().get(activationRequest.playerId());
        player.setActive(activationRequest.activate());
        return player;
    }

    @MessageMapping("/player/ticket/pickUp")
    @SendTo("/topic/player/ticket/pickUp")
    public Player pickUpTicket(PlayerRequest playerRequest) {
        Player player = devastation.getBoard().getPlayers().get(playerRequest.playerId());
        devastation.getBoard().pickUpTicket(player);
        return player;
    }

    @MessageMapping("/player/ticket/drop")
    @SendTo("/topic/player/ticket/drop")
    public Player dropTicket(PlayerRequest playerRequest) {
        Player player = devastation.getBoard().getPlayers().get(playerRequest.playerId());
        devastation.getBoard().dropTicket(player);
        return player;
    }

    @SendTo("/topic/player/burnOut")
    public Player broadcastPlayerBurnOut(Player player) {
        return player;
    }

    @SendTo("/topic/player/revive")
    public Player broadcastPlayerRevive(Player player) {
        return player;
    }

    @MessageMapping("/players")
    @SendTo("/topic/players")
    public Player[] getPlayers() {
        return devastation.getBoard().getPlayers().values().toArray(new Player[0]);
    }

    @SendTo("/topic/scoreUpdate")
    public static int broadcastScoreUpdate(int score) {
        return score;
    }

    @SendTo("/topic/ticket/create")
    public static Ticket broadcastTicketCreate(Ticket ticket) {
        System.out.println("Broadcasting");
        System.out.println(ticket.toString());
        return ticket;
    }

    @SendTo("/topic/ticket/resolve")
    public static Ticket broadcastTicketResolve(Ticket ticket) {
        return ticket;
    }

    @SendTo("/topic/ticket/task/completionUpdate")
    public static TaskProgressBroadcast broadcastTaskCompletion(TaskProgressBroadcast tpb) {
        return tpb;
    }

    @MessageMapping("/tickets")
    @SendTo("/topic/tickets")
    public Ticket[] getTickets() {
        return devastation.getBoard().getTickets().values().toArray(new Ticket[0]);
    }

    @MessageMapping("/stations")
    @SendTo("/topic/stations")
    public Station[] getStations() {
        return devastation.getBoard().getStations().values().toArray(new Station[0]);
    }

    @MessageMapping("/tiles")
    @SendTo("/topic/tiles")
    public Tile[][] getTiles() {
        return devastation.getBoard().getBoard();
    }
}
