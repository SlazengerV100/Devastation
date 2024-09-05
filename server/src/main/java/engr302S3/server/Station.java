package engr302S3.server;

import lombok.Getter;

/**
 * Represents a work station in the game.
 */
public class Station {
    @Getter
    private final StationType stationType;
    private int progress;

    /**
     * Create a work station with no task on it.
     * @param stationType the type of station.
     */
    Station(StationType stationType) {
        this.stationType = stationType;
        progress = 0;
    }

    /**
     * Whether the station is currently in use.
     * @return {@code true} if the station is in use, {@code false} otherwise.
     */
    public boolean inUse() {
        return false;
    }

    /**
     * Increment the progress indicator by one.
     * @return {@code true} if the task is now complete as a result of the increment, {@code false} if the task is still in progress.
     * @throws IllegalStateException if there is no task in progress on this station.
     */
    public boolean progress() {
        progress++;
        return false;
    }

    /**
     * Reset progress counter to zero.
     */
    private void resetProgress() {
        progress = 0;
    }
}
