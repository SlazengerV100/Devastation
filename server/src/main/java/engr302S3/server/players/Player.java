package engr302S3.server.players;

import engr302S3.server.Position;
import engr302S3.server.ticketFactory.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
public abstract class Player {

    public enum Role {
        PROJECT_MANAGER,
        DEVELOPER,
        TESTER
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public Position getTranslation() {
            return switch (this) {
                case LEFT -> new Position(-1, 0);
                case UP -> new Position(0,-1);
                case DOWN -> new Position(0, 1);
                case RIGHT -> new Position(1, 0);
            };
        }
    }


    private final Role role;
    @Getter @Setter private Position position;
    @Getter @Setter private Direction direction;
    @Getter @Setter private boolean active;
    @Getter @Setter private Optional<Ticket> heldTicket; //setter doubles as pickup (no conditions needed)

    public Player(Role role, Position position, boolean active) {
        this.role = role;
        this.position = position;
        this.active = active;
        this.heldTicket = Optional.empty();
    }

    /**
     * If player is not facing direction specified first input will face him that direction
     *
     * @param direction to move player in
     */
    public void movePlayer(Direction direction) {

        if (this.direction != direction) {
            setDirection(direction);
        } else {
            this.setPosition(this.position.add(direction.getTranslation()));
        }
    }

    /**
     * Drop currently held ticket, need to implement conditions for dropping at location (dependent on role)
     */
    public void dropTicket() {
        this.heldTicket.ifPresent(ticket -> ticket.setPosition(this.position.add(direction.getTranslation())));
    }

    @Override
    public String toString() {
        return "Player role: " + role + ", Position: (" + position.x() + ", " + position.y() + "), Active: " + active;
    }
}

