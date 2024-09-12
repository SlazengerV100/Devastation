package engr302S3.server;

import engr302S3.server.players.Player;
import engr302S3.server.Station;
import engr302S3.server.ticketFactory.Ticket;
import lombok.Getter;

/**
 * Game tiles in a devastation game
 */
@Getter
public class Tile {
  private final Position position;  // The tile's position on the board (x, y coordinates)
  private TileType type;         // Enum to define the content type of the tile (e.g., Player, Station)
  private Object content;        // Stores the actual content of the tile (Player, Station, or Ticket)

  /**
   * Constructor to create a tile at the given position.
   * Initializes the tile as empty with no content.
   *
   * @param position The position of the tile (x, y coordinates).
   */
  public Tile(Position position) {
    this.position = position;
    this.type = TileType.EMPTY; // Initialize the tile as empty
    this.content = null;        // No content initially
  }

  /**
   * Method to check if the tile is empty.
   *
   * @return {@code true} if the tile is empty, {@code false} otherwise.
   */
  public boolean empty() {
    return type == TileType.EMPTY; // Check if the tile type is empty
  }

  /**
   * Method to set the tile's content as a Station.
   *
   * @param station The Station object to set on the tile.
   */
  public void setStation(Station station) {
    this.type = TileType.STATION;  // Set the tile type as Station
    this.content = station;        // Store the station in the content
  }

  /**
   * Method to set the tile's content as a Player.
   *
   * @param player The Player object to set on the tile.
   */
  public void setPlayer(Player player) {
    this.type = TileType.PLAYER;  // Set the tile type as Player
    this.content = player;        // Store the player in the content
  }

  /**
   * Method to set the tile's content as a Ticket.
   *
   * @param ticket The Ticket object to set on the tile.
   */
  public void setTicket(Ticket ticket) {
    this.type = TileType.TICKET;  // Set the tile type as Ticket
    this.content = ticket;        // Store the ticket in the content
  }

  /**
   * Method to clear the tile, setting it back to empty with no content.
   */
  public void clearTile() {
    this.type = TileType.EMPTY;  // Set the tile type back to empty
    this.content = null;         // Remove any content from the tile
  }

}