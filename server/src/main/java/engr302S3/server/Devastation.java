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
  private Tile[][] board; // A 2D array to represent the game board
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
    // Declare the game board as a 2D array
    this.board = new Tile[boardHeight][boardWidth];

    // Initialize the game board with empty tiles
    for (int x = 0; x < boardHeight; x++) {
      for (int y = 0; y < boardWidth; y++) {
        board[x][y] = new Tile(new Position(x, y)); // Create a tile at each point (x, y)
      }
    }

    initialiseObjects();

  }

  /**
   * initialise starting positions of game objects
   */
  public void initialiseObjects(){
    this.activeTickets = new ArrayList<>();
    this.ticketFactory = new TicketFactory();
    this.players = new ArrayList<>();
    this.stations = new ArrayList<>();

    Player projectManager = new ProjectManager(new Position(1, 1));
    Player developer = new Developer(new Position(2, 1));
    Player tester = new Tester(new Position(3, 1));
    // Add players
    this.players.add(projectManager);
    this.players.add(developer);
    this.players.add(tester);

    // Initialise player positions to tiles
    board[1][1].setPlayer(projectManager);
    board[2][1].setPlayer(developer);
    board[3][1].setPlayer(tester);

    // Add Stations
    for (StationType type : StationType.values()) {
      Station s = new Station(type);
      this.stations.add(s);
    }

    // Temporary station positions
    board[9][4].setStation(stations.get(0));
    board[9][10].setStation(stations.get(1));
    board[9][16].setStation(stations.get(2));
    board[16][4].setStation(stations.get(3));
    board[16][10].setStation(stations.get(4));
    board[16][16].setStation(stations.get(5));

    // Generate one active ticket for testing and set tile to ticket
    board[2][3].setTicket(generateTicket());
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
      return board[x][y]; // Return the tile at the specified coordinates.
    }
    return null; // Return null if the coordinates are out of bounds.
  }

  /**
   * Generate a ticket and add it to the active tickets
   */
  public Ticket generateTicket(){
    Ticket ticket = ticketFactory.getTicket();
    activeTickets.add(ticket);
    return ticket;
  }

}