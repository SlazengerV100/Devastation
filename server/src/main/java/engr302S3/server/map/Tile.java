package engr302S3.server.map;

import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Ticket;

import lombok.Getter;

/**
 * Game tiles in a devastation game
 */
@Getter
public class Tile {

    private final Position position;
    private TileType type;
    private Object content; // Stores the actual content of the tile (Player, Station, or Ticket)

    /**
     * Constructor to create a tile at the given position.
     * Initializes the tile as empty with no content.
     *
     * @param position The position of the tile (x, y coordinates).
     */
    public Tile(Position position) {
        this.position = position;
        this.type = TileType.EMPTY;
        this.content = null;
    }

    /**
     * Method to check if the tile is empty.
     *
     * @return {@code true} if the tile is empty, {@code false} otherwise.
     */
    public boolean empty() {
        return type == TileType.EMPTY;
    }

    /**
     * Method to check if the tile contains a ticket.
     *
     * @return {@code true} if the tiles contents are a ticket, {@code false} otherwise.
     */
    public boolean containsTicket() {
        return content instanceof Ticket;
    }

    /**
     * Method to set the tile's content as a Station.
     *
     * @param station The Station object to set on the tile.
     */
    public void setStation(Station station) {
        this.type = TileType.STATION;
        this.content = station;
    }

    /**
     * Method to set the tile's content as a Player.
     *
     * @param player The Player object to set on the tile.
     */
    public void setPlayer(Player player) {
        this.type = TileType.PLAYER;
        this.content = player;
    }

    /**
     * Method to set the tile's content as a Ticket.
     *
     * @param ticket The Ticket object to set on the tile.
     */
    public void setTicket(Ticket ticket) {
        this.type = TileType.TICKET;
        this.content = ticket;
    }

    /**
     * Method to clear the tile, setting it back to empty with no content.
     */
    public void clearTile() {
        this.type = TileType.EMPTY;
        this.content = null;
    }

    @Override
    public String toString() {
        return switch (type) {
            case STATION -> "_S_|";
            case PLAYER -> "_P_|";
            case TICKET -> "_T_|";
            case EMPTY -> "_*_|";
        };
    }
}