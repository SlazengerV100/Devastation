package engr302S3.server.players;

import engr302S3.server.map.Position;
import engr302S3.server.ticketFactory.Ticket;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Abstract class representing players
 */
@Getter @Setter
public abstract class Player {

    /**
     * Possible roles
     */
    public enum Role {
        PROJECT_MANAGER,
        DEVELOPER,
        TESTER
    }

    /**
     * Player movement directions
     */
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public Position getTranslation() {
            return switch (this) {
                case LEFT -> new Position(-1, 0);
                case UP -> new Position(0, -1);
                case DOWN -> new Position(0, 1);
                case RIGHT -> new Position(1, 0);
            };
        }
    }

    private final Role role;
    private Position position;
    private Direction direction;
    private boolean active;
    private Optional<Ticket> heldTicket; //setter doubles as pickup (no conditions needed)

    /**
     * Player Constructor
     *
     * @param role of the player
     * @param position of the player initially
     */
    public Player(Role role, Position position) {
        this.role = role;
        this.position = position;
        this.direction = Direction.RIGHT;
        this.active = false;
        this.heldTicket = Optional.empty();
    }

    /**
     * If player is not facing direction specified first input will face him that direction
     *
     * @param direction to move player in
     */
    public void movePlayer(Direction direction) {

        if (!active) {
           return;
        }

        if (this.direction != direction) {
            setDirection(direction);
        } else {

            Position position;

            try {
                position = this.position.add(getDirection().getTranslation());
            } catch (IllegalArgumentException e) {
                return; //cannot move if out of bounds
            }

            this.setPosition(position);
        }
    }

    @Override
    public String toString() {
        return "Player role: " + role + ", Position: (" + position.x() + ", " + position.y() + "), Active: " + active;
    }
}