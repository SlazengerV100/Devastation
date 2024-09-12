package engr302S3.server;

import engr302S3.server.ticketFactory.Ticket;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for initialising game board and managing game details
 */
@Getter
@Component
public class Devastation {
  private List<List<Tile>> board; // A 2D list to represent the game board
  private int boardWidth;         // Width of the game board
  private int boardHeight;        // Height of the game board
  @Setter private int score;      // Game score
  @Setter private boolean running;        // Flag to indicate if the game is running
  private int timeLeft = 300;           // Time left for the game (seconds)
  private List<Ticket> activeTickets;

  @PostConstruct
  public void init() {
    setup();
  }

  /**
   * Initial setup configuration for the game
   */
  public void setup(){
    this.boardWidth = 10;
    this.boardHeight = 10;

    this.board = new ArrayList<>();

    // Initialize the game board with empty tiles.
    for (int y = 0; y < height; y++) {
      List<Tile> row = new ArrayList<>(); // Create a new row for each height level.
      for (int x = 0; x < width; x++) {
        row.add(new Tile(new Point(x, y))); // Create a tile at each point (x, y).
      }
      board.add(row); // Add the row to the board.
    }
  }

  public void decreaseTime(){
    timeLeft --;
  }

  /**
   * Method to retrieve a specific tile at the given coordinates.
   *
   * @param x The x-coordinate of the tile.
   * @param y The y-coordinate of the tile.
   * @return The tile at the given coordinates, or null if out of bounds.
   */
  public Tile getTileAt(int x, int y) {
    if (x >= 0 && x < boardWidth && y >= 0 && y < boardHeight) {
      return board.get(y).get(x); // Return the tile at the specified coordinates.
    }
    return null; // Return null if the coordinates are out of bounds.
  }
}