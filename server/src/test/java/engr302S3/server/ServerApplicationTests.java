package engr302S3.server;

import engr302S3.server.map.Board;
import engr302S3.server.map.Position;
import engr302S3.server.map.StationType;
import engr302S3.server.players.Player;
import engr302S3.server.players.ProjectManager;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServerApplicationTests {

    @Test
    public void testPrintBoard() {
        File file = new File("src/main/resources/map._TempStations.csv");


        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("File not found: " + e.getMessage());
        }

        Devastation devastation = new Devastation();

        System.out.println(devastation.getBoard());
    }

    @Test
    public void testMovement() {

        Player player = new ProjectManager(new Position(1, 1));
        player.setActive(true);

        assertEquals(new Position(1, 1), player.getPosition(), "Initial position should be (1, 1)");

        player.movePlayer(Player.Direction.RIGHT);
        assertEquals(new Position(2, 1), player.getPosition(), "Player should move to the right to (2, 1)");

        player.movePlayer(Player.Direction.LEFT);
        assertEquals(new Position(2, 1), player.getPosition(), "Player should still be at (2, 1)");
        assertEquals(Player.Direction.LEFT, player.getDirection(), "Player should now be facing left");

        player.movePlayer(Player.Direction.LEFT);
        assertEquals(new Position(1, 1), player.getPosition(), "Player should move back to (1, 1)");
    }

    @Test
    public void testTicketFactory() {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            tickets.add(TicketFactory.getTicket());
        }

        tickets.forEach(e -> System.out.println(e.getTasks().size()));

        assertTrue(tickets.stream().anyMatch(e -> e.getTasks().size() == 1),
                "There should be at least one ticket with exactly 1 task");
        assertTrue(tickets.stream().anyMatch(e -> e.getTasks().size() == StationType.values().length),
                "There should be at least one ticket with a number of tasks equal to the number of StationType values");
    }

    @Test
    public void testDropTicket() {
        Devastation devastation = new Devastation();
        Board board = devastation.getBoard();
        long key = board.getPlayers().keySet().stream().sorted().findFirst().get();
        Player player = board.getPlayers().get(key);
        player.setActive(true);

        board.getBoard()[Board.BOARD_WIDTH / 2 - 1][Board.BOARD_HEIGHT / 6].setTicket(TicketFactory.getTicket());

        assertTrue(board.getBoard()[Board.BOARD_WIDTH / 2 - 1][Board.BOARD_HEIGHT / 6].containsTicket(),
                "The tile should contain a ticket");

        player.movePlayer(Player.Direction.LEFT);
        board.pickUpTicket(player);

        assertFalse(board.getBoard()[Board.BOARD_WIDTH / 2 - 1][Board.BOARD_HEIGHT / 6].containsTicket(),
                "The ticket should have been picked up and no longer be on the tile");

        board.dropTicket(player, devastation);

        assertTrue(board.getBoard()[Board.BOARD_WIDTH / 2 - 1][Board.BOARD_HEIGHT / 6].containsTicket(),
                "The tile should contain the dropped ticket");
    }

    @Test
    public void testMovementBelowZero() {
        Devastation devastation = new Devastation();
        Board board = devastation.getBoard();
        long key = board.getPlayers().keySet().stream().sorted().findFirst().get();
        Player player = board.getPlayers().get(key);

        for (int i = 0; i < 50; i++) {
            player.movePlayer(Player.Direction.LEFT);
        }

        System.out.print(player.getPosition());
        assertTrue(player.getPosition().x() >= 0 && player.getPosition().y() >= 0,
                "Player position should not be below zero after multiple movements");
    }

    @Test
    void contextLoads() {
        // This test ensures the Spring context loads successfully
    }
}
