package engr302S3.server.map;

import engr302S3.server.players.Developer;
import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Task;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class StationTests {
    private Board board;
    private Player player;
    private Ticket ticket;
    private Station station;

    @BeforeEach
    public void setUp() {
        board = Board.testingBoard();
        player = board.getPlayers().values().stream()
                .filter(p -> p instanceof Developer)
                .findFirst()
                .orElse(null);
        station = board.getStations().values().stream()
                .findFirst()
                .orElse(null);
        // Get a ticket that has a task for the above station
        do {
            ticket = TicketFactory.getTicket();
        } while (ticket.getTasks().stream().map(Task::getType).noneMatch(stationType -> stationType.equals(station.getStationType())));

        player.setHeldTicket(Optional.of(ticket));
        board.movePlayer(player, Player.Direction.DOWN);
        board.dropTicket(player);
    }

    @Test
    public void testTicketPlacement() {
        assertTrue(station.inUse());
        assertEquals(Optional.of(ticket), station.getTicketWorkingOn());
    }

    @Test
    public void testProgress() {
        station.progress();
        assertEquals(1, station.getProgress(), "Station progress should be 1 after one increment");
    }

    @Test
    public void testProgressCompletion() {
        Task task = station.getRelevantTask(ticket).get();

        // Simulate the completion of task
        for (int i = 0; i < task.getCompletionTimeTotal() - 1; i++) {
            station.progress();
            task.updateCompletion();
            assertFalse(task.getCompleted());
        }
        station.progress();
        task.updateCompletion();
        assertTrue(task.getCompleted());
    }

    @Test
    public void testGetRelevantTask() {
        Task task = station.getRelevantTask(ticket).get();
        assertEquals(station.getStationType(), task.getType());
        assertTrue(ticket.getTasks().contains(task));
    }

    @Test
    public void testTicketPickUpFromStation() {
        board.pickUpTicket(player);
        assertEquals(Optional.of(ticket), player.getHeldTicket());
        assertTrue(station.getTicketWorkingOn().isEmpty());
        assertFalse(station.inUse());
        for (Tile tile : station.getTiles()) {
            assertFalse(tile.containsTicket(), "Station tile should not have a ticket after pick up");
            assertEquals(tile.getType(), TileType.STATION);
        }
    }
}
