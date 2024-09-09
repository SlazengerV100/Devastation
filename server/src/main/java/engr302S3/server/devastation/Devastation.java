package engr302S3.server.devastation;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class used for initialising game board and managing game details
 */
@Getter
public class Devastation {
  private List<List<Tile>> board; // A 2D list to represent the game board
  private int boardWidth;         // Width of the game board
  private int boardHeight;        // Height of the game board
  @Setter private int score;      // Game score
  @Setter private boolean running;        // Flag to indicate if the game is running
  private int timeLeft;           // Time left for the game (countdown)

  /**
   * Constructor to create a game board of specified size.
   *
   * @param width  The width of the game board.
   * @param height The height of the game board.
   */
  public Devastation(int width, int height) {
    this.boardWidth = width;
    this.boardHeight = height;
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

  /**
   * Method to run the game, only includes 5-minute countdown and score initialisation.
   */
  public void runGame(){
    this.score = 0;
    try {
      for (timeLeft = 300; timeLeft > 0; timeLeft--) { // Countdown from 300 seconds (5 minutes)
        System.out.println(timeLeft + "..."); // Print the remaining seconds.
        Thread.sleep(1000); // Wait for 1 second before next iteration.
      }
    } catch (InterruptedException e) {
      e.printStackTrace(); // Handle interrupted exception
    }
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
