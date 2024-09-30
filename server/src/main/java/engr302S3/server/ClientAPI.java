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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ClientAPI {
    private Devastation devastation;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    ClientAPI(Devastation devastation) {
        this.devastation = devastation;
    }

    @MessageMapping("/player/move")
    @SendTo("/topic/player/move")
    public Player movePlayer(Movement movementRequest) {
        Player player = devastation.getBoard().getPlayers().get(movementRequest.playerId());
        devastation.getBoard().movePlayer(player, movementRequest.direction());
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
        Optional<Ticket> ticket = player.getHeldTicket();
        devastation.getBoard().dropTicket(player, devastation);
        if(ticket.isPresent() && ticket.get().isComplete()){
            this.broadcastScoreUpdate(devastation.getScore());
            this.broadcastTicketResolve(ticket.get());
        }
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
    public int broadcastScoreUpdate(int score) {
        messagingTemplate.convertAndSend("/topic/scoreUpdate", score);
        return score;
    }

    @SendTo("/topic/ticket/create")
    public Ticket broadcastTicketCreate(Ticket ticket) {
        messagingTemplate.convertAndSend("/topic/ticket/create", ticket);
        return ticket;
    }

    @SendTo("/topic/ticket/resolve")
    public Ticket broadcastTicketResolve(Ticket ticket) {
        messagingTemplate.convertAndSend("/topic/ticket/resolve", ticket);
        return ticket;
    }

    @SendTo("/topic/ticket/task/completionUpdate")
    public TaskProgressBroadcast broadcastTaskCompletion(TaskProgressBroadcast tpb) {
        messagingTemplate.convertAndSend("/topic/ticket/task/completionUpdate", tpb);
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