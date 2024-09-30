package engr302S3.server.map;

import engr302S3.server.players.Developer;
import engr302S3.server.players.Player;
import engr302S3.server.players.ProjectManager;
import engr302S3.server.players.Tester;
import engr302S3.server.ticketFactory.Ticket;

import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Getter
public class Board {

    public static int BOARD_WIDTH;
    public static int BOARD_HEIGHT;

    private final Tile[][] board;
    private final Map<Long, Player> players;
    private final Map<Long, Station> stations;
    private final Map<Long, Ticket> tickets;

    public Board() {
        this.board = createBoard();

        this.players = new HashMap<>();
        this.stations = new HashMap<>();
        this.tickets = new HashMap<>();

        createPlayers();
        createStations();
    }

    /**
     * Create the board with different tiles.
     *
     * @return the board
     */
    public Tile[][] createBoard() {
        ArrayList<String[]> stations = loadFiles("src/main/resources/map._TempStations.csv");
        ArrayList<String[]> walls = loadFiles("src/main/resources/map._Wall.csv");

        // Assuming both walls and stations are guaranteed to have the same dimensions
        BOARD_HEIGHT = walls.size();
        BOARD_WIDTH = walls.get(0).length;

        System.out.println(BOARD_WIDTH + " " + BOARD_HEIGHT);

        String[][] combined = new String[BOARD_WIDTH][BOARD_HEIGHT];

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                //.get here needs to be y,x to be read as intended
                String wallTile = walls.get(y)[x];
                String stationTile = stations.get(y)[x];

                // Prioritise wall tiles over station tiles if present
                combined[x][y] = wallTile.equals("160") ? wallTile : stationTile;
            }
        }

        Tile[][] board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

        //load initial tiles
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                switch (combined[x][y]) {
                    case "-1" -> board[x][y] = new Tile(new Position(x, y)); // Empty tile
                    case "33" -> board[x][y] = new Tile(new Position(x, y), TileType.STATION); // Station tile
                    case "160" -> board[x][y] = new Tile(new Position(x, y), TileType.WALL);  // Wall tile
                }
            }
        }

        // Print the board for debugging
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                System.out.print(board[x][y] + " ");
            }
            System.out.println();  // New line after each row
        }

        return board;
    }


    /**
     * Load a csv and scan it.
     *
     * @param path of the file
     * @return the csv in arraylist format
     */
    private ArrayList<String[]> loadFiles(String path) {

        ArrayList<String[]> lines = new ArrayList<>();

        try {

            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                lines.add(scanner.nextLine().split(","));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return lines;
    }

    /**
     * Set up the board with positions of developers.
     */
    private void createPlayers() {
        // Define player positions
        Position projectManagerPosition = new Position(BOARD_WIDTH / 4, BOARD_HEIGHT / 2);
        Position developerPosition = new Position(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
        Position testerPosition = new Position(BOARD_WIDTH - 3, BOARD_HEIGHT / 2);

        ProjectManager projectManager = new ProjectManager(projectManagerPosition);
        Developer developer = new Developer(developerPosition);
        Tester tester = new Tester(testerPosition);

        // Add players to the players map
        this.players.put(projectManager.getId(), projectManager);
        this.players.put(developer.getId(), developer);
        this.players.put(tester.getId(), tester);

        // Initialise player positions on the board
        board[projectManagerPosition.x()][projectManagerPosition.y()].setPlayer(projectManager);
        board[developerPosition.x()][developerPosition.y()].setPlayer(developer);
        board[testerPosition.x()][testerPosition.y()].setPlayer(tester);
    }


    /**
     * Create stations in a 2*2 radius of a Station tile on map.
     */
    public void createStations() {

        Set<Position> finishedStations = new HashSet<>();

        for (StationType type : StationType.values()) {
            Station station = new Station(type);
            this.stations.put(station.getId(), station);
        }

        long id = 0;

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {

                if (finishedStations.contains(new Position(x, y))) {
                    continue;
                }

                if (board[x][y].getType() == TileType.STATION) {

                    board[x][y].setStation(stations.get(id));
                    board[x + 1][y].setStation(stations.get(id));
                    board[x][y + 1].setStation(stations.get(id));
                    board[x + 1][y + 1].setStation(stations.get(id));

                    finishedStations.add(new Position(x + 1, y));
                    finishedStations.add(new Position(x, y + 1));
                    finishedStations.add(new Position(x + 1, y + 1));

                    id++;
                }
            }
        }
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
        tile.clearTile();
    }

    /**
     * Drop currently held ticket of player, may change parameter to string for specific player
     */
    public void dropTicket(Player player) {

        Position position;

        if (player.getHeldTicket().isEmpty()) {
            return;
        }

        try {
            position = player.getDirection().getTranslation(player.getPosition());
        } catch (IllegalArgumentException e) {
            return; //Do nothing if the position is out of bounds
        }

        Ticket ticket = player.getHeldTicket().get();

        player.getHeldTicket().ifPresent(x -> x.setPosition(position));
        player.setHeldTicket(Optional.empty());
        board[position.x()][position.y()].setTicket(ticket);
    }

    public void movePlayer(Player player, Player.Direction direction) {

        Position previous = player.getPosition();
        Position position;

        try {
            if (direction == player.getDirection()) {
                position = direction.getTranslation(player.getPosition());
            } else {
                position = player.getPosition();
            }
        } catch (IllegalArgumentException e) {
            return; //Do nothing if position is out of bounds
        }

        if (board[position.x()][position.y()].getType() == TileType.WALL || board[position.x()][position.y()].getType() == TileType.STATION || board[position.x()][position.y()].getType() == TileType.TICKET) {
            return;
        }

        position = player.movePlayer(direction);

        board[previous.x()][previous.y()].clearTile();
        board[position.x()][position.y()].setPlayer(player); //will not change player location if only direction is changed
    }

    /**
     * Add a ticket to the board
     * @param id the ticket ID
     * @param ticket the ticket Object
     */
    public boolean addTicket(long id, Ticket ticket) {
        Tile t = this.getTileAt(ticket.getPosition());
        if (t.empty()) {
            t.setTicket(ticket);
            tickets.put(id, ticket);
            return true;
        }
        return false;
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

        for (int x = 0; x < BOARD_WIDTH; x++) {
            sb.append("|");
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                sb.append(board[x][y].toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}