package engr302S3.server.ticketFactory;

import engr302S3.server.map.Position;

import engr302S3.server.map.Station;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Ticket contains a collection of tasks for the players to complete. Tickets keep track of how
 * long they have been active for the purpose of scoring.
 */
@Getter
public class Ticket {
    private static long idTracker = 1;
    private final long id;
    private final ArrayList<Task> tasks;
    private final String ticketTitle;
    private int totalTime;
    @Setter private Position position;
    private final double blowOutProb;
    @Setter private Optional<Station> station;

    Ticket(String title, int totalTime, double blowOutProb, ArrayList<Task> tasks) {
        id = idTracker++;
        this.ticketTitle = title;
        this.totalTime = totalTime;
        this.blowOutProb = blowOutProb;
        this.tasks = tasks;
    }

    /**
     * Increments time the Ticket has been active.
     */
    public void incrementTime() {
        totalTime++;
    }
}