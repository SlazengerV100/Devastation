package engr302S3.server;

import ch.qos.logback.core.net.server.Client;
import engr302S3.server.map.*;
import engr302S3.server.playerActions.TaskProgressBroadcast;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class responsible for ensuring any objects that rely on time get updated accordingly.
 */
@Component
public class ScheduledTasks {
    private final ClientAPI clientAPI;

    /**
     * Injects the ClientAPI component into this component
     *
     */
    @Autowired
    public ScheduledTasks(ClientAPI clientAPI) {
        this.clientAPI = clientAPI;
    }

    /**
     * For every 1000 milliseconds that elapses, ensure that Spring Boot updates the game time,
     * Ticket age, and Task completion
     */
    @Scheduled(fixedRate = 1000)
    public void updateGameTime() {
        //update the game clock
        clientAPI.getDevastation().decreaseTime();
        //check each tile for a ticket, and update the ticket timer if there is one
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {

            for (int x = 0; x < Board.BOARD_WIDTH; x++) {

                if (clientAPI.getDevastation().getBoard().getTileAt(new Position(x, y)).getType() == TileType.TICKET) {
                    ((Ticket) clientAPI.getDevastation().getBoard().getTileAt(new Position(x, y)).getContent()).incrementTime();
                }
            }
        }
        //update the stations and tasks that they are working on
        for (Station station : clientAPI.getDevastation().getBoard().getStations().values()) {
            if (station.progress()) {
                clientAPI.broadcastTaskCompletion(new TaskProgressBroadcast(station.getTicketWorkingOn().get(), station.getStationType()));
            }
        }
    }

    /**
     * Every 5second try to generate a new ticket if there is room of the board
     */
    @Scheduled(fixedRate = 5000)
    public void createTicket() {

        for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
            //check the first column of the board for generated tickets, I am assuming this is
            //where we will  spawn them
            Tile tile = clientAPI.getDevastation().getBoard().getTileAt(new Position(0, i));

            if (tile.empty()) {
                Ticket ticket = TicketFactory.getTicket();
                ticket.setPosition(tile.getPosition());
                tile.setTicket(ticket);
                clientAPI.getDevastation().getBoard().addTicket(ticket.getId(), ticket);
                clientAPI.broadcastTicketCreate(ticket);
                break;
            }
        }
    }
}