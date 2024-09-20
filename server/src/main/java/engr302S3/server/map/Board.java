package engr302S3.server.map;

import engr302S3.server.players.Developer;
import engr302S3.server.players.Player;
import engr302S3.server.players.ProjectManager;
import engr302S3.server.players.Tester;

import engr302S3.server.ticketFactory.Ticket;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class Board {

    public static final int BOARD_WIDTH = 30;
    public static final int BOARD_HEIGHT = 20;

    private final Tile[][] board;
    private final ArrayList<Player> players; //changed to map dependent on information from frontend
    private final ArrayList<Station> stations;

    public Board() {
        this.board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                board[x][y] = new Tile(new Position(x, y)); // Create a tile at each point (x, y)
            }
        }

        this.players = new ArrayList<>(3);
        this.stations = new ArrayList<>(StationType.values().length);
        setup();
    }

    /**
     * Set up the board with positions of developers and stations
     */
    private void setup() {

        // Add players to list
        this.players.add(new ProjectManager(new Position(BOARD_WIDTH/4, BOARD_HEIGHT/2)));
        this.players.add(new Developer(new Position(BOARD_WIDTH/2, BOARD_HEIGHT/2)));
        this.players.add(new Tester(new Position((BOARD_WIDTH/4) * 3, BOARD_HEIGHT/2)));

        // Initialise player positions to tiles
        board[BOARD_WIDTH/4][BOARD_HEIGHT/2].setPlayer(players.get(0));
        board[BOARD_WIDTH/2][BOARD_HEIGHT/2].setPlayer(players.get(1));
        board[(BOARD_WIDTH/4) * 3][BOARD_HEIGHT/2].setPlayer(players.get(2));

        // Add Stations
        for (StationType type : StationType.values()) {
            this.stations.add(new Station(type));
        }

        // Station positions
        board[BOARD_WIDTH/2][BOARD_HEIGHT /3].setStation(stations.get(0));
        board[BOARD_WIDTH/2 + 1][BOARD_HEIGHT /2].setStation(stations.get(1));
        board[BOARD_WIDTH/2][BOARD_HEIGHT *2/3].setStation(stations.get(2));
        board[BOARD_WIDTH*2/3][BOARD_HEIGHT /3].setStation(stations.get(3));
        board[BOARD_WIDTH*2/3][BOARD_HEIGHT /2].setStation(stations.get(4));
        board[BOARD_WIDTH*2/3][BOARD_HEIGHT *2/3].setStation(stations.get(5));
    }

    /**
     * Pick up item on requested player
     *
     * @param player to requested to pick up item
     */
    public void pickUpTicket(Player player) { //This can be changed to string etc or some other way to get players

        Position position = player.getPosition().add(player.getDirection().getTranslation());
        Tile tile = board[position.x()][position.y()];

        if (tile.empty() || !tile.containsTicket()) {
            return;
        }

        Ticket ticket = (Ticket) tile.getContent();
        player.setHeldTicket(Optional.ofNullable(ticket));
        player.getHeldTicket().get().setPosition(player.getPosition());
        tile.clearTile();
    }

    /**
     * Drop currently held ticket, need to implement conditions for dropping at location (dependent on role)
     */
    public void dropTicket(Player player) {
        player.getHeldTicket().ifPresent(ticket -> ticket.setPosition(player.getPosition().add(player.getDirection().getTranslation())));
    }

    /**
     * Method to retrieve a specific tile at the given coordinates.
     *
     * @param position The coordinates of the tile.
     * @return The tile at the given coordinates
     */
    public Tile getTileAt(Position position) {
        return board[position.x()][position.y()];
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("_____".repeat(BOARD_WIDTH));

        sb.append("\n");

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            sb.append("|");
            for (int x = 0; x < BOARD_WIDTH; x++) {
                sb.append(board[x][y].toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
