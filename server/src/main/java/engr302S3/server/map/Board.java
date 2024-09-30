package engr302S3.server.map;

import engr302S3.server.players.Developer;
import engr302S3.server.players.Player;
import engr302S3.server.players.ProjectManager;
import engr302S3.server.players.Tester;
import engr302S3.server.ticketFactory.Ticket;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class Board {

    public static final int BOARD_WIDTH = 30;
    public static final int BOARD_HEIGHT = 20;

    private final Tile[][] board;
    private final Map<Long, Player> players;
    private final Map<Long, Station> stations;
    private final Map<Long, Ticket> tickets;

    public Board() {
        this.board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                board[x][y] = new Tile(new Position(x, y)); // Create a tile at each point (x, y)
            }
        }

        this.players = new HashMap<>();
        this.stations = new HashMap<>();
        this.tickets = new HashMap<>();
        setup();
    }

    /**
     * Set up the board with positions of developers and stations
     */
    private void setup() {

        // Add players
        ProjectManager projectManager = new ProjectManager(new Position(BOARD_WIDTH/4, BOARD_HEIGHT/2));
        Developer developer = new Developer(new Position(BOARD_WIDTH/2, BOARD_HEIGHT/2));
        Tester tester = new Tester(new Position((BOARD_WIDTH/4) * 3, BOARD_HEIGHT/2));
        this.players.put(projectManager.getId(), projectManager);
        this.players.put(developer.getId(), developer);
        this.players.put(tester.getId(), tester);

        // Initialise player positions to tiles
        board[BOARD_WIDTH/4][BOARD_HEIGHT/2].setPlayer(projectManager);
        board[BOARD_WIDTH/2][BOARD_HEIGHT/2].setPlayer(developer);
        board[(BOARD_WIDTH/4) * 3][BOARD_HEIGHT/2].setPlayer(tester);

        // Add stations
        Station frontEnd = new Station(StationType.FRONTEND);
        Station backEnd = new Station(StationType.BACKEND);
        Station api = new Station(StationType.API);
        Station unitTesting = new Station(StationType.UNIT_TESTING);
        Station coverageTesting = new Station(StationType.COVERAGE_TESTING);
        Station staticAnalysis = new Station(StationType.STATIC_ANALYSIS);
        this.stations.put(frontEnd.getId(), frontEnd);
        this.stations.put(backEnd.getId(), backEnd);
        this.stations.put(api.getId(), api);
        this.stations.put(unitTesting.getId(), unitTesting);
        this.stations.put(coverageTesting.getId(), coverageTesting);
        this.stations.put(staticAnalysis.getId(), staticAnalysis);

        // Initialise station positions to tiles
        board[BOARD_WIDTH/2][BOARD_HEIGHT /3].setStation(frontEnd);
        board[BOARD_WIDTH/2 + 1][BOARD_HEIGHT /2].setStation(backEnd);
        board[BOARD_WIDTH/2][BOARD_HEIGHT *2/3].setStation(api);
        board[BOARD_WIDTH*2/3][BOARD_HEIGHT /3].setStation(unitTesting);
        board[BOARD_WIDTH*2/3][BOARD_HEIGHT /2].setStation(coverageTesting);
        board[BOARD_WIDTH*2/3][BOARD_HEIGHT *2/3].setStation(staticAnalysis);
    }

    /**
     * Pick up item on requested player
     *
     * @param player to requested to pick up item
     */
    public void pickUpTicket(Player player) { //This can be changed to string etc. or some other way to get players

        Position position = player.getDirection().getTranslation(player.getPosition());
        Tile tile = board[position.x()][position.y()];

        if (tile.empty() || !tile.containsTicket()) {
            return;
        }

        Ticket ticket = (Ticket) tile.getContent();
        player.setHeldTicket(Optional.ofNullable(ticket));
        player.getHeldTicket().get().setPosition(player.getPosition());

        // If ticket is being worked on set the station it is at
        if(tile.getType().equals(TileType.STATION)){
            ticket.setStation(Optional.empty());
        }

        tile.clearTile();
    }

    /**
     * Drop currently held ticket of player, may change parameter to string for specific player
     */
    public void dropTicket(Player player) {

        Position position;

        try {
            position = player.getDirection().getTranslation(player.getPosition());
        } catch (IllegalArgumentException e) {
            return; //Do nothing if the position is out of bounds
        }

        if (player.getHeldTicket().isEmpty()) {
            return;
        }

        Ticket ticket = player.getHeldTicket().get();

        player.getHeldTicket().ifPresent(x -> x.setPosition(position));
        player.setHeldTicket(Optional.empty());

        // Set ticket station is working on if not in use
        if (getTileAt(position).getType().equals(TileType.STATION)){
            Station station = (Station) getTileAt(position).getContent();
            if(!station.inUse()){
                station.setTicketWorkingOn(Optional.of(ticket));
                ticket.setStation(Optional.of(station));
            }
        }

        board[position.x()][position.y()].setTicket(ticket);
    }

    /**
     * Add a ticket to the board
     * @param id the ticket ID
     * @param ticket the ticket Object
     */
    public void addTicket(long id, Ticket ticket) {
        tickets.put(id, ticket);
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
