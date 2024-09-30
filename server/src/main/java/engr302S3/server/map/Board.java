package engr302S3.server.map;

import engr302S3.server.Devastation;
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

        BOARD_WIDTH = walls.size();
        BOARD_HEIGHT = walls.get(0).length;

        String[][] combined = new String[BOARD_WIDTH][BOARD_HEIGHT];

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                String tile = Objects.equals(walls.get(x)[y], "160") ? walls.get(x)[y] : stations.get(x)[y];
                combined[x][y] = tile;
            }
        }

        Tile[][] board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                switch (combined[x][y]) {
                    case "-1" -> board[x][y] = new Tile(new Position(x, y));
                    case "33" -> board[x][y] = new Tile(new Position(x, y), TileType.STATION);
                    case "160" -> board[x][y] = new Tile(new Position(x, y), TileType.WALL);
                }
            }
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
        // Add players
        ProjectManager projectManager = new ProjectManager(new Position(BOARD_WIDTH/2,BOARD_HEIGHT/6));
        Developer developer = new Developer(new Position(BOARD_WIDTH/2,BOARD_HEIGHT/2));
        Tester tester = new Tester(new Position(BOARD_WIDTH/2,(BOARD_HEIGHT/6) * 5));
        this.players.put(projectManager.getId(), projectManager);
        this.players.put(developer.getId(), developer);
        this.players.put(tester.getId(), tester);

        // Initialise player positions to tiles
        board[BOARD_WIDTH/2][BOARD_HEIGHT/6].setPlayer(projectManager);
        board[BOARD_WIDTH/2][BOARD_HEIGHT/2].setPlayer(developer);
        board[BOARD_WIDTH/2][(BOARD_HEIGHT/6) * 5].setPlayer(tester);
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
    public void dropTicket(Player player, Devastation game) {

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
        board[position.x()][position.y()].setTicket(ticket);

        // If ticket is placed at end of board and it is completed then update the game score and clear the ticket
        if(position.x() == (BOARD_WIDTH-1) && ticket.isComplete()){
            getTileAt(position).empty();
            game.updateScore(ticket);
        }
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