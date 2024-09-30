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
    private final Devastation game;
    private final ClientAPI clientAPI;

    /**
     * Injects the devastation component into this component
     *
     * @param devastation
     */
    @Autowired
    public ScheduledTasks(Devastation devastation, ClientAPI clientAPI) {
        this.clientAPI = clientAPI;
        this.game = devastation;
    }

    /**
     * For every 1000 milliseconds that elapses, ensure that Spring Boot updates the game time,
     * Ticket age, and Task completion
     */
    @Scheduled(fixedRate = 1000)
    public void updateGameTime() {
        //update the game clock
        game.decreaseTime();
        //check each tile for a ticket, and update the ticket timer if there is one
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {

            for (int x = 0; x < Board.BOARD_WIDTH; x++) {

                if (game.getBoard().getTileAt(new Position(x, y)).getType() == TileType.TICKET) {
                    ((Ticket) game.getBoard().getTileAt(new Position(x, y)).getContent()).incrementTime();
                }
            }
        }
        //update the stations and tasks that they are working on
        for (Station station : game.getBoard().getStations().values()) {
            if (station.progress()) {
                clientAPI.broadcastTaskCompletion(new TaskProgressBroadcast(station.getTicketWorkingOn().get(), station.getStationType()));
            }
        }
    }

    /**
     * Every 5second try to generate a new ticket if there is room of the board
     */
    @Scheduled(fixedRate = 8000)
    public void createTicket() {
        // Generate random x and y coordinates within the project manager area
        int randomX = 1 + (int) (Math.random() * 8);
        int randomY = 1 + (int) (Math.random() * 13);

        // Get the tile at the random position
        Tile tile = game.getBoard().getTileAt(new Position(randomX, randomY));
        Ticket ticket = TicketFactory.getTicket();
        ticket.setPosition(tile.getPosition());
        // Try to add the ticket to the board
        if (game.getBoard().addTicket(ticket.getId(), ticket)) {

            tile.setTicket(ticket);
            clientAPI.broadcastTicketCreate(ticket);
        } else {
            //tile is not free
            System.out.println("Tile at (" + randomX + ", " + randomY + ") is not empty. Ticket not created.");
        }
    }
}