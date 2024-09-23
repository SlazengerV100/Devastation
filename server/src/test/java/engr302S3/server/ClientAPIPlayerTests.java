package engr302S3.server;

import engr302S3.server.map.Position;
import engr302S3.server.playerActions.*;
import engr302S3.server.players.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientAPIPlayerTests {
    private Devastation devastation;
    private ClientAPI api;

    @BeforeEach
    public void setUp() {
        devastation = new Devastation();
        api = new ClientAPI(devastation);
    }

    @Test
    public void testMovePlayer_changeDirection() {
        Player player = devastation.getBoard().getPlayers().values().stream().findFirst().get();
        Position initialPosition = player.getPosition();
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition, newPosition);
        assertEquals(Player.Direction.DOWN, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveDown() {
        Player player = devastation.getBoard().getPlayers().values().stream().findFirst().get();
        Position initialPosition = player.getPosition();
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition.x(), newPosition.x());
        assertEquals(initialPosition.y() + 1, newPosition.y());
        assertEquals(Player.Direction.DOWN, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveUp() {
        Player player = devastation.getBoard().getPlayers().values().stream().findFirst().get();
        Position initialPosition = player.getPosition();
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.UP));
        api.movePlayer(new Movement(player.getId(), Player.Direction.UP));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition, newPosition);
        assertEquals(Player.Direction.UP, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveRight() {
        Player player = devastation.getBoard().getPlayers().values().stream().findFirst().get();
        Position initialPosition = player.getPosition();
        api.movePlayer(new Movement(player.getId(), Player.Direction.RIGHT));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition.x() + 1, newPosition.x());
        assertEquals(initialPosition.y(), newPosition.y());
        assertEquals(Player.Direction.RIGHT, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveLeft() {
        Player player = devastation.getBoard().getPlayers().values().stream().findFirst().get();
        Position initialPosition = player.getPosition();
        api.movePlayer(new Movement(player.getId(), Player.Direction.RIGHT));
        api.movePlayer(new Movement(player.getId(), Player.Direction.LEFT));
        api.movePlayer(new Movement(player.getId(), Player.Direction.LEFT));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition, newPosition);
        assertEquals(Player.Direction.LEFT, player.getDirection());
    }
}
