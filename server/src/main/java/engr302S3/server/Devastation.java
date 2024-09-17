package engr302S3.server;

import engr302S3.server.players.Player;
import engr302S3.server.players.Developer;
import engr302S3.server.players.Tester;
import engr302S3.server.players.ProjectManager;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;
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
  private final int boardWidth = 20;         // Width of the game board
  private final int boardHeight = 20;        // Height of the game board
  @Setter private int score;      // Game score
  @Setter private boolean running;        // Flag to indicate if the game is running
  private int timeLeft = 300;           // Time left for the game (seconds)
  private TicketFactory ticketFactory;
  private List<Ticket> activeTickets;
  private List<Player> players;
  private List<Station> stations;



  /**
   * Ensures that this @Component runs setup() as soon as the Spring Boot Server boots up.
   * This can be replaced by a @Configuration Class that can not only create this Devastation
   * Component, but handle other Component/Beans as well.
   */
  @PostConstruct
  public void init() {
    setup();
  }

  /**
   * Initial setup configuration for the game
   */
  public void setup(){

    // Initialise game objects
    this.board = new ArrayList<>();
    this.activeTickets = new ArrayList<>();
    this.ticketFactory = new TicketFactory();
    this.players = new ArrayList<>();
    this.stations = new ArrayList<>();

    // Initialize the game board with empty tiles.
    for (int y = 0; y < boardHeight; y++) {
      List<Tile> row = new ArrayList<>(); // Create a new row for each height level.
      for (int x = 0; x < boardWidth; x++) {
        row.add(new Tile(new Position(x, y))); // Create a tile at each point (x, y).
      }
      board.add(row); // Add the row to the board.
    }

    // Add players
    this.players.add(new ProjectManager(new Position(1, 1)));
    this.players.add(new Developer(new Position(2, 1)));
    this.players.add(new Tester(new Position(3, 1)));

    for (StationType type : StationType.values()) {
      this.stations.add(new Station(type));
    }

    generateTicket();


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

  public void generateTicket(){
    activeTickets.add(ticketFactory.getTicket());
  }
}