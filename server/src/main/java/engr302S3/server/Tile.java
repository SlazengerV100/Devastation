package engr302S3.server;

import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Ticket;

public class Tile {
    private final Room room;
    private final int x;
    private final int y;
    private Player player;
    private Station station;
    private Ticket ticket;

    public Tile(Room room, int x, int y) {
        this.room = room;
        this.x = x;
        this.y = y;
    }

    public enum Room {
        PLANNING,
        DEVELOPMENT,
        TESTING
    }
}
