package engr302S3.server.map;

import engr302S3.server.ticketFactory.Task;
import engr302S3.server.ticketFactory.Ticket;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

/**
 * Represents a work station in the game.
 */
@Getter
public class Station {

    private static long idTracker;
    private final long id;
    private final StationType stationType;
    private int progress;
    private List<Tile> tiles;
    @Setter private Optional<Ticket> ticketWorkingOn;

    /**
     * Create a work station with no task on it.
     *
     * @param stationType the type of station.
     */
    public Station(StationType stationType, List<Tile> tiles) {
        id = idTracker++;
        this.stationType = stationType;
        this.tiles = tiles;
        this.progress = 0;
        ticketWorkingOn = Optional.empty();
    }

    /**
     * Whether the station is currently in use.
     *
     * @return {@code true} if the station is in use, {@code false} otherwise.
     */
    public boolean inUse() {
        return ticketWorkingOn.isPresent();
    }

    /**
     * Increment the progress indicator by one.
     *
     * @return {@code true} if the task is now complete as a result of the increment, {@code false} if the task is still in progress or there is no task to progress.
     */
    public boolean progress() {
        if (ticketWorkingOn.isEmpty()) {
            return false;
        }

        progress++;
        return getRelevantTask(ticketWorkingOn.get()).map(task -> progress >= task.getCompletionTime()).orElse(false);
    }

    /**
     * Get the task that is relevant to this station
     *
     * @return the task that is associated with this station, or an {@link java.util.Optional#empty() Optional.empty()} if there is no task for this station.
     */
    private Optional<Task> getRelevantTask(Ticket ticket) {
        return ticket.getTasks().stream().filter(task -> task.getType().equals(stationType)).findFirst();
    }

    /**
     * Reset progress counter to zero.
     */
    private void resetProgress() {
        progress = 0;
    }
}