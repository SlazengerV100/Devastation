package engr302S3.server;

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

    /**
     * Injects the devastation component into this component
     *
     * @param devastation
     */
    @Autowired
    public ScheduledTasks(Devastation devastation) {
        this.game = devastation;
    }

    /**
     * For every 1000 milliseconds that elapses, ensure that Spring Boot updates the game time,
     * Ticket age, and Task completion
     */
    @Scheduled(fixedRate = 1000)
    public void updateGameTime() {
        if (!game.isRunning()) {return;}
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
                ClientAPI.broadcastTaskCompletion(new TaskProgressBroadcast(station.getTicketWorkingOn().get(), station.getStationType()));
            }
        }
    }

    /**
     * Every 20second try to generate a new ticket if there is room of the board
     */
    @Scheduled(fixedRate = 1000)
    public void createTicket() {
        if (!game.isRunning()) {return;}
        for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
            //check the first column of the board for generated tickets, I am assuming this is
            //where we will  spawn them
            Tile tile = game.getBoard().getTileAt(new Position(0, i));

            if (tile.empty()) {
                System.out.println("creating ticket");
                Ticket ticket = TicketFactory.getTicket();
                tile.setTicket(ticket);
                game.getBoard().addTicket(ticket.getId(), ticket);
                ClientAPI.broadcastTicketCreate(ticket);
                break;
            }
        }
    }
}