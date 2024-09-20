package engr302S3.server;

import engr302S3.server.map.Position;
import engr302S3.server.map.Station;
import engr302S3.server.map.StationType;
import engr302S3.server.map.Tile;
import engr302S3.server.players.Player;
import engr302S3.server.players.Developer;
import engr302S3.server.players.Tester;
import engr302S3.server.players.ProjectManager;

import jakarta.annotation.PostConstruct;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Class used for initialising game board and managing game details
 */
@Component
@Getter
public class Devastation {

    @Getter(AccessLevel.NONE) private final Tile[][] board; // A 2D array to represent the game board
    private final ArrayList<Player> players;
    private final ArrayList<Station> stations;

    private final int boardWidth = 30;         // Width of the game board
    private final int boardHeight = 20;        // Height of the game board
    @Setter private int score;      // Game score
    @Setter private boolean running;        // Flag to indicate if the game is running
    @Getter(AccessLevel.NONE) private int timeLeft = 300;           // Time left for the game (seconds)


    public Devastation() {
        this.board = new Tile[boardWidth][boardHeight];
        this.players = new ArrayList<>(3);
        this.stations = new ArrayList<>(StationType.values().length);

        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                board[x][y] = new Tile(new Position(x, y)); // Create a tile at each point (x, y)
            }
        }
    }

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
     * initialise starting positions of game objects
     */
    public void setup(){

        //create players and their starting positions
        Player projectManager = new ProjectManager(new Position(2, 1));
        Player developer = new Developer(new Position(3, 1));
        Player tester = new Tester(new Position(4, 1));

        // Add players to list
        this.players.add(projectManager);
        this.players.add(developer);
        this.players.add(tester);

        // Initialise player positions to tiles
        board[2][1].setPlayer(projectManager);
        board[3][1].setPlayer(developer);
        board[4][1].setPlayer(tester);

        // Add Stations
        for (StationType type : StationType.values()) {
            this.stations.add(new Station(type));
        }

        // Station positions
        board[boardWidth/2][boardHeight/3].setStation(stations.get(0));
        board[boardWidth/2][boardHeight/2].setStation(stations.get(1));
        board[boardWidth/2][boardHeight*2/3].setStation(stations.get(2));
        board[boardWidth*2/3][boardHeight/3].setStation(stations.get(3));
        board[boardWidth*2/3][boardHeight/2].setStation(stations.get(4));
        board[boardWidth*2/3][boardHeight*2/3].setStation(stations.get(5));
    }

    public void decreaseTime() {
        timeLeft --;
    }

    /**
     * Method to retrieve a specific tile at the given coordinates.
     *
     * @param position The coordinates of the tile.
     * @return The tile at the given coordinates, or null if out of bounds.
     */
    public Tile getTileAt(Position position) {

        if (position.x() >= 0 && position.x() < boardWidth && position.y() >= 0 && position.y() < boardHeight) {
            return board[position.x()][position.y()]; // Return the tile at the specified coordinates.
        }

        return null; // Return null if the coordinates are out of bounds.
    }
}