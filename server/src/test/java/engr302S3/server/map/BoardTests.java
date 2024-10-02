package engr302S3.server.map;

import engr302S3.server.players.Player;
import engr302S3.server.players.Developer;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private Board board;

    @BeforeEach
    public void setUp() {
        // A simple 5x5 map with one wall and one station tile
        String mapData = """
                -1,-1,-1,-1,-1
                -1,-1,-1,-1,-1
                -1,-1,160,-1,-1
                -1,-1,-1,-1,-1
                -1,-1,-1,-1,-1
                """;

        // Create the board from CSV string
        board = new Board(mapData);
    }

    @Test
    public void testBoardCreation() {
        // Assert that the board was created correctly with the correct dimensions
        assertEquals(5, Board.BOARD_WIDTH);
        assertEquals(5, Board.BOARD_HEIGHT);

        // Verify that walls and station tiles are placed correctly
        assertEquals(TileType.WALL, board.getTileAt(2, 2).getType()); // Wall at center
        assertEquals(TileType.EMPTY, board.getTileAt(0, 0).getType()); // Empty tile at top-left
    }

    @Test
    public void testInvalidMove() {
        // Move developer into a wall
        Player developer = board.getPlayers().values().stream()
                .filter(p -> p instanceof Developer)
                .findFirst()
                .orElse(null);
        assertNotNull(developer);

        // Place developer near the wall at (2, 2)
        developer.setTile(board.getTileAt(1, 2));

        // Move developer into the wall tile at (2, 2)
        Tile initialTile = developer.getTile();
        assertSame(developer.getDirection(), Player.Direction.RIGHT);
        board.movePlayer(developer, Player.Direction.RIGHT); // Assuming RIGHT moves towards (2, 2)

        // Verify that the player did not move into the wall
        Tile newTile = developer.getTile();
        assertSame(initialTile, newTile);
    }

    @Test
    public void testDropTicket() {
        Player developer = board.getPlayers().values().stream()
                .filter(p -> p instanceof Developer)
                .findFirst()
                .orElse(null);
        assertNotNull(developer);
        //dev on 2 2
        Tile t = board.getTileAt(2, 2);
        developer.setTile(t);

        // Create a ticket and have the developer hold it
        Ticket ticket = TicketFactory.getTicket();
        developer.setHeldTicket(Optional.of(ticket));
        developer.setDirection(Player.Direction.DOWN);
        // Drop the ticket
        Ticket droppedTicket = board.dropTicket(developer);

        // Verify the ticket was dropped on the tile below the player
        assertNotNull(droppedTicket);
        assertEquals(board.getTileAt(2,3), droppedTicket.getTile().get());
        assertFalse(developer.getHeldTicket().isPresent());
    }

    @Test
    public void testMovePlayer() {
        // Move the developer to an empty tile
        Player developer = board.getPlayers().values().stream()
                .filter(p -> p instanceof Developer)
                .findFirst()
                .orElse(null);
        assertNotNull(developer);

        // Place developer at (1,1)
        Tile startTile = board.getTileAt(1, 1);
        developer.setTile(startTile);

        // Move developer right to (1,2)
        Tile initialTile = developer.getTile();
        board.movePlayer(developer, Player.Direction.RIGHT);

        // Verify that the player moved to the correct tile
        Tile newTile = developer.getTile();
        assertNotSame(initialTile, newTile);
    }

    @Test
    public void testPickUpTicket() {
        // Place a ticket on the station tile (4, 4)
        Ticket ticket = TicketFactory.getTicket();
        Tile stationTile = board.getTileAt(4, 4);
        ticket.setTile(Optional.of(stationTile));
        board.addTicket(1L, ticket);

        // Get the developer and move them to the tile with the ticket
        Player developer = board.getPlayers().values().stream()
                .filter(p -> p instanceof Developer)
                .findFirst()
                .orElse(null);
        assertNotNull(developer);

        // Move developer to the station tile
        developer.setTile(stationTile);
        board.pickUpTicket(developer);

        // Verify that the developer is now holding the ticket
        assertTrue(developer.getHeldTicket().isPresent());
        assertEquals(ticket, developer.getHeldTicket().get());

        // Verify that the tile no longer has a ticket
        assertFalse(stationTile.containsTicket());
    }
}
