package engr302S3.server;

import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Ticket;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Tile {
    private final Room room;
    private final int x;
    private final int y;
    @Setter private Player player;
    @Setter private Station station;
    @Setter private Ticket ticket;

    public Tile(Room room, int x, int y) {
        this.room = room;
        this.x = x;
        this.y = y;
    }

    public enum Room {
        PLANNING,
        DEVELOPMENT,
        TESTING,
        WALL,
        EMPTY
    }
}
