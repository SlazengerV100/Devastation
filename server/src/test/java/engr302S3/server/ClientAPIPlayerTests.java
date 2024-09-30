package engr302S3.server;

import engr302S3.server.map.Position;
import engr302S3.server.map.Tile;
import engr302S3.server.map.TileType;
import engr302S3.server.playerActions.*;
import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class ClientAPIPlayerTests {
    private Devastation devastation;
    private ClientAPI api;
    private Player player;
    private Position initialPosition;

    @BeforeEach
    public void setUp() {
        devastation = new Devastation();
        api = new ClientAPI(devastation);
        player = devastation.getBoard().getPlayers().values().stream().min(Comparator.comparing(Player::getId)).get();
        player.setActive(true);
        initialPosition = player.getPosition();
    }

    @Test
    public void testMovePlayer_changeDirection() {
        Tile initialTile = devastation.getBoard().getTileAt(initialPosition);
        assertFalse(initialTile.empty());
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition, newPosition);
        assertEquals(Player.Direction.DOWN, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveDown() {
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition.x(), newPosition.x());
        assertEquals(initialPosition.y() + 1, newPosition.y());
        assertEquals(Player.Direction.DOWN, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveUp() {
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
        api.movePlayer(new Movement(player.getId(), Player.Direction.RIGHT));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition.x() + 1, newPosition.x());
        assertEquals(initialPosition.y(), newPosition.y());
        assertEquals(Player.Direction.RIGHT, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveLeft() {
        api.movePlayer(new Movement(player.getId(), Player.Direction.RIGHT));
        api.movePlayer(new Movement(player.getId(), Player.Direction.LEFT));
        api.movePlayer(new Movement(player.getId(), Player.Direction.LEFT));
        Position newPosition = player.getPosition();
        assertEquals(initialPosition, newPosition);
        assertEquals(Player.Direction.LEFT, player.getDirection());
    }

    @Test
    public void testActivatePlayer_activate() {
        api.activatePlayer(new Activation(player.getId(), true));
        assertTrue(player.isActive());
    }

    @Test
    public void testActivatePlayer_deactivate() {
        api.activatePlayer(new Activation(player.getId(), false));
        assertFalse(player.isActive());
    }

    private void testPickUpTicket(Player player) {
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        Ticket ticket = TicketFactory.getTicket();
        Position ticketPosition = new Position(initialPosition.x(), initialPosition.y() + 1);
        ticket.setPosition(ticketPosition);
        devastation.getBoard().getTileAt(ticketPosition).setTicket(ticket);
        if (player.getDirection() != Player.Direction.DOWN) {
            api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        }
        api.pickUpTicket(new PlayerRequest(player.getId()));
        assertEquals(initialPosition, player.getPosition());
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        assertEquals(ticket, player.getHeldTicket().get());
        assertEquals(TileType.EMPTY, devastation.getBoard().getTileAt(ticketPosition).getType());
    }

    @Test
    public void testPickUpTicket_valid() {
        testPickUpTicket(player);
    }

    @Test
    public void testPickUpTicket_noTicketPresent() {
        Position noTicketPosition = new Position(initialPosition.x(), initialPosition.y() + 1);
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        api.pickUpTicket(new PlayerRequest(player.getId()));
        assertEquals(initialPosition, player.getPosition());
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        assertTrue(player.getHeldTicket().isEmpty());
        assertEquals(TileType.EMPTY, devastation.getBoard().getTileAt(noTicketPosition).getType());
    }

    @Test
    public void testPickUpTicket_wrongDirection() {
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        Ticket ticket = TicketFactory.getTicket();
        Position ticketPosition = new Position(initialPosition.x(), initialPosition.y() + 1);
        ticket.setPosition(ticketPosition);
        devastation.getBoard().getTileAt(ticketPosition).setTicket(ticket);
        if (player.getDirection() != Player.Direction.UP) {
            api.movePlayer(new Movement(player.getId(), Player.Direction.UP));
        }
        api.pickUpTicket(new PlayerRequest(player.getId()));
        assertEquals(initialPosition, player.getPosition());
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        assertTrue(player.getHeldTicket().isEmpty());
        assertEquals(TileType.EMPTY, devastation.getBoard().getTileAt(new Position(initialPosition.x(), initialPosition.y() - 1)).getType());
        assertEquals(TileType.TICKET, devastation.getBoard().getTileAt(ticketPosition).getType());
    }

    @Test
    public void testDropTicket_noTicketPresent() {
        api.dropTicket(new PlayerRequest(player.getId()));
        assertEquals(initialPosition, player.getPosition());
        assertEquals(TileType.PLAYER, devastation.getBoard().getTileAt(initialPosition).getType());
        assertTrue(player.getHeldTicket().isEmpty());
        Position noTicketPosition = new Position(initialPosition.x(), initialPosition.y() + 1);
        assertEquals(TileType.EMPTY, devastation.getBoard().getTileAt(noTicketPosition).getType());
    }
}