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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ServerApplicationTests {

    @Test
    public void testMovement() {

        Player player = new ProjectManager(new Position(1, 1));
        player.setActive(true);

        assert new Position(1, 1).equals(player.getPosition());

        player.movePlayer(Player.Direction.RIGHT);

        assert new Position(2, 1).equals(player.getPosition());

        player.movePlayer(Player.Direction.LEFT);

        assert new Position(2, 1).equals(player.getPosition());
        assert player.getDirection().equals(Player.Direction.LEFT);

        player.movePlayer(Player.Direction.LEFT);

        assert new Position(1, 1).equals(player.getPosition());

        try {
            player.movePlayer(Player.Direction.LEFT);
        } catch (IllegalArgumentException e) {
            System.out.println("Success");
        }
    }

    @Test
    public void testTicketFactory() {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            tickets.add(TicketFactory.getTicket());
        }

        tickets.forEach(e -> System.out.println(e.getTasks().size()));

        assert tickets.stream().anyMatch(e -> e.getTasks().size() == 1);
        assert tickets.stream().anyMatch(e -> e.getTasks().size() == StationType.values().length);
    }

    @Test
    public void testDevastationMap() {
        Devastation devastation = new Devastation();

        Board board = devastation.getBoard();
        long key = board.getPlayers().keySet().stream().sorted().findFirst().get();
        Player player = board.getPlayers().get(key);

        board.getBoard()[Board.BOARD_WIDTH/4 - 1][Board.BOARD_HEIGHT/2].setTicket(TicketFactory.getTicket());
        player.movePlayer(Player.Direction.LEFT);

        assert player.getPosition().equals( board.getBoard()[Board.BOARD_WIDTH/4][Board.BOARD_HEIGHT/2].getPosition());

        System.out.println(devastation.getBoard());

        board.pickUpTicket(player);

        System.out.println(devastation.getBoard());

        Position position =  player.getDirection().getTranslation(player.getPosition());

        assert !board.getBoard()[position.x()][position.y()].containsTicket();
    }

    @Test
    public void testDropTicket() {

        Devastation devastation = new Devastation();

        Board board = devastation.getBoard();
        long key = board.getPlayers().keySet().stream().sorted().findFirst().get();
        Player player = board.getPlayers().get(key);
        player.setActive(true);
        board.getBoard()[Board.BOARD_WIDTH/4 - 1][Board.BOARD_HEIGHT/2].setTicket(TicketFactory.getTicket());
        player.movePlayer(Player.Direction.LEFT);

        board.pickUpTicket(player);

        System.out.println(devastation.getBoard());

        assert !board.getBoard()[Board.BOARD_WIDTH/4 - 1][Board.BOARD_HEIGHT/2].containsTicket();

        board.dropTicket(player, devastation);

        System.out.println(devastation.getBoard());

        assert board.getBoard()[Board.BOARD_WIDTH/4 - 1][Board.BOARD_HEIGHT/2].containsTicket();
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
    }

    @Test
    void contextLoads() {
    }

}
