package engr302S3.server.players;

import engr302S3.server.ticketFactory.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
public abstract class Player {

    // Enum for role
    public enum Role {
        PROJECT_MANAGER,
        DEVELOPER,
        TESTER
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public enum State {
        IDLE, WORKING
    }

    // Getters and Setters for role, x, y, and active
    // Fields to store the role, position, and active status of the player
    private final Role role;
    @Getter @Setter private int x, y;
    @Getter @Setter private boolean active;
    @Getter @Setter private Optional<Ticket> heldTicket; //setter doubles as pickup (no conditions needed)
    @Getter @Setter private State state;
    @Getter @Setter private Direction direction;

    // Constructor
    public Player(Role role, int x, int y, boolean active) {
        this.role = role;
        this.x = x;
        this.y = y;
        this.heldTicket = Optional.empty();
        this.active = active;
        this.direction = Direction.DOWN;
        this.state = State.IDLE;
    }

    /**
     * Drop currently held ticket, need to implement conditions for dropping at location (dependent on role)
     */
    public abstract void dropTicket();

    @Override
    public String toString() {
        return "Player role: " + role + ", Position: (" + x + ", " + y + "), Active: " + active;
    }
}

