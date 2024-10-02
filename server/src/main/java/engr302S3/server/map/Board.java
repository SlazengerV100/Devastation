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
    private final Map<Ticket, Tile> ticketTiles;

    public Board() {
        this.board = createBoardFromFiles("src/main/resources/map._TempStations.csv", "src/main/resources/map._Wall.csv");

        this.players = new HashMap<>();
        this.stations = new HashMap<>();
        this.tickets = new HashMap<>();
        this.ticketTiles = new HashMap<>();

        createPlayers();
        createStations();
    }

    // New constructor that loads the board from a CSV string
    public Board(String csvData) {
        this.board = createBoardFromCsv(csvData);

        this.players = new HashMap<>();
        this.stations = new HashMap<>();
        this.tickets = new HashMap<>();
        this.ticketTiles = new HashMap<>();

        createPlayers();
        createStations();
    }

    // Method to create a board from CSV file paths
    public Tile[][] createBoardFromFiles(String stationPath, String wallPath) {
        ArrayList<String[]> stations = loadFiles(stationPath);
        ArrayList<String[]> walls = loadFiles(wallPath);

        return combineBoardData(walls, stations);
    }

    // Method to create a board from a CSV string
    public Tile[][] createBoardFromCsv(String csvData) {
        ArrayList<String[]> lines = new ArrayList<>();
        Scanner scanner = new Scanner(csvData);

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine().split(","));
        }

        // Reuse the same board generation logic for CSV string data
        return combineBoardData(lines, lines); // You can customize stations vs. walls here if needed
    }

    // Helper method to combine data and generate the board
    private Tile[][] combineBoardData(ArrayList<String[]> walls, ArrayList<String[]> stations) {
        BOARD_HEIGHT = walls.size();
        BOARD_WIDTH = walls.get(0).length;

        String[][] combined = new String[BOARD_WIDTH][BOARD_HEIGHT];

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                String wallTile = walls.get(y)[x];
                String stationTile = stations.get(y)[x];

                // Prioritize wall tiles over station tiles if present
                combined[x][y] = wallTile.equals("160") ? wallTile : stationTile;
            }
        }

        Tile[][] board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

        // Load initial tiles
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                switch (combined[x][y]) {
                    case "-1" -> board[x][y] = new Tile(x, y); // Empty tile
                    case "33" -> board[x][y] = new Tile(x, y, TileType.STATION); // Station tile
                    case "160" -> board[x][y] = new Tile(x, y, TileType.WALL);  // Wall tile
                }
            }
        }

        return board;
    }

    // Method to load a CSV file
    private ArrayList<String[]> loadFiles(String path) {
        ArrayList<String[]> lines = new ArrayList<>();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
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
        int shift = BOARD_WIDTH / 6;

        Tile projectManagerTile = board[shift][BOARD_HEIGHT / 2];
        Tile developerTile = board[shift * 3][BOARD_HEIGHT / 2];
        Tile testerTile = board[shift * 5][BOARD_HEIGHT / 2];

        ProjectManager projectManager = new ProjectManager(projectManagerTile);
        Developer developer = new Developer(developerTile);
        Tester tester = new Tester(testerTile);

        // Add players to the players map
        this.players.put(projectManager.getId(), projectManager);
        this.players.put(developer.getId(), developer);
        this.players.put(tester.getId(), tester);
    }


    /**
     * Create stations in a 2*2 radius of a Station tile on map.
     */
    public void createStations() {
        Set<Tile> finishedStations = new HashSet<>();
        StationType[] types = StationType.values();
        int typeIndex = 0;

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (finishedStations.contains(board[x][y])) {
                    continue;
                }

                if (board[x][y].getType() == TileType.STATION) {
                    List<Tile> stationTiles = new ArrayList<>();
                    StationType type = types[typeIndex++];
                    stationTiles.add(board[x][y]);
                    stationTiles.add(board[x][y + 1]);
                    stationTiles.add(board[x + 1][y]);
                    stationTiles.add(board[x + 1][y + 1]);

                    Station station = new Station(type, stationTiles);
                    this.stations.put(station.getId(), station);

                    finishedStations.add(board[x + 1][y]);
                    finishedStations.add(board[x][y + 1]);
                    finishedStations.add(board[x + 1][y + 1]);
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

        Tile tile = getTranslation(player.getTile(), player.getDirection());
        Optional<Ticket> ticketOptional = getTicketOnTile(tile);
        // No ticket to pick up
        if (ticketOptional.isEmpty()) {
            return;
        }
        Ticket ticket = ticketOptional.get();
        // When picked up, set ticket position to none
        ticket.setTile(Optional.empty());
        player.setHeldTicket(Optional.ofNullable(ticket));
        
        if (ticket.isInFinishedZone()) {
            ticket.setInFinishedZone(false);
        }

        tile.clearTile();
    }

    /**
     * Drop currently held ticket of player, may change parameter to string for specific player
     */
    public Ticket dropTicket(Player player) {

        Tile tile;

        if (player.getHeldTicket().isEmpty()) {
            return null;
        }

        try {
            tile = getTranslation(player.getTile(), player.getDirection());
        } catch (IllegalArgumentException e) {
            return null; //Do nothing if the position is out of bounds
        }

        Ticket ticket = player.getHeldTicket().get();

        ticket.setTile(Optional.of(tile));
        tile.setType(TileType.TICKET);
        player.setHeldTicket(Optional.empty());
        System.out.println("called");
//
//        Optional<Station> stationOptional = getStationOnTile(tile);
//        // Set ticket station is working on if not in use
//        if (stationOptional.isPresent()) {
//            Station station = stationOptional.get();
//            if (!station.inUse()) {
//                station.setTicketWorkingOn(Optional.of(ticket));
//            }
//        }
//        // If ticket in final column and is complete then remove from map and set tile to empty
//        if (tile.getX() == BOARD_WIDTH - 1) {
//            ticket.setInFinishedZone(true);
//            if (ticket.isComplete()) {
//                tickets.remove(ticket.getId());
//                tile.empty();
//            }
//        }

        return ticket;
    }

    /**
     * Brute force all the tickets to find a ticket on the tile next to the player.
     *
     * @param tile the tile desired
     * @return the ticket on the tile if there is one
     */
    private Optional<Ticket> getTicketOnTile(Tile tile) {
        return tickets.values().stream()
                .filter(t -> t.getTile().isPresent() && t.getTile().get().equals(tile))
                .findFirst();
    }

    /**
     * Brute force all the stations to find a station on the tile next to the player.
     *
     * @param tile the tile desired
     * @return the station on the tile if there is one
     */
    private Optional<Station> getStationOnTile(Tile tile) {
        return stations.values().stream()
                .filter(s -> s.getTiles().stream().anyMatch(t -> t.equals(tile)))
                .findFirst();
    }

    public void movePlayer(Player player, Player.Direction direction) {
        Tile currentTile = player.getTile();
        Tile targetTile;

        //turn the player, don't move him
        if(direction != player.getDirection()){
            player.setDirection(direction);
            return;
        }

        // Determine target tile based on direction
        targetTile = getTranslation(currentTile, direction);
        if (isInvalidTile(targetTile)) return;

        // Update player's tile to new tile
        player.setTile(targetTile);
        currentTile.clearTile();
    }

    private boolean isInvalidTile(Tile tile) {
        return tile.getType() == TileType.WALL ||
                tile.getType() == TileType.STATION ||
                tile.getType() == TileType.TICKET;
    }


    private Tile getTranslation(Tile tile, Player.Direction direction) {
        try{
            return switch (direction) {
                case LEFT -> board[tile.getX() - 1][tile.getY()];
                case UP -> board[tile.getX()][tile.getY() - 1];
                case DOWN -> board[tile.getX()][tile.getY() + 1];
                case RIGHT -> board[tile.getX() + 1][tile.getY()];
            };
        } catch (ArrayIndexOutOfBoundsException b){
            return null;
        }
    }

    /**
     * Add a ticket to the board
     *
     * @param id     the ticket ID
     * @param ticket the ticket Object
     */
    public boolean addTicket(long id, Ticket ticket) {
        if (ticket.getTile().isPresent()) {
            Tile t = ticket.getTile().get();
            if (t.empty()) {
                tickets.put(id, ticket);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to retrieve a specific tile at the given coordinates.
     *
     * @param x The position of the tile (x coordinates).
     * @param y The position of the tile (y coordinates).
     * @return The tile at the given coordinates
     */
    public Tile getTileAt(int x, int y) {
        return board[x][y];
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