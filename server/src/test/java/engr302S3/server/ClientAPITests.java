package engr302S3.server;

import engr302S3.server.map.Tile;
import engr302S3.server.map.TileType;
import engr302S3.server.playerActions.*;
import engr302S3.server.players.Player;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClientAPITests {
    private Devastation devastation;
    private ClientAPI api;
    private Player player;
    private Tile initialTile;

    @BeforeEach
    public void setUp() {
        devastation = new Devastation();
        api = new ClientAPI(devastation);
        player = devastation.getBoard().getPlayers().values().stream().min(Comparator.comparing(Player::getId)).get();
        player.setActive(true);
        initialTile = player.getTile();
    }

    @Test
    public void testMovePlayer_changeDirection() {
        assertFalse(initialTile.empty());
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        Tile newTile = player.getTile();
        assertEquals(this.initialTile, newTile);
        assertEquals(Player.Direction.DOWN, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveDown() {
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        Tile newTile = player.getTile();
        assertEquals(initialTile.getX(), newTile.getX());
        assertEquals(initialTile.getY() + 1, newTile.getY());
        assertEquals(Player.Direction.DOWN, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveUp() {
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        api.movePlayer(new Movement(player.getId(), Player.Direction.UP));
        api.movePlayer(new Movement(player.getId(), Player.Direction.UP));
        Tile newTile = player.getTile();
        assertEquals(initialTile, newTile);
        assertEquals(Player.Direction.UP, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveRight() {
        api.movePlayer(new Movement(player.getId(), Player.Direction.RIGHT));
        Tile newTile = player.getTile();
        assertEquals(initialTile.getX() + 1, newTile.getX());
        assertEquals(initialTile.getY(), newTile.getY());
        assertEquals(Player.Direction.RIGHT, player.getDirection());
    }

    @Test
    public void testMovePlayer_moveLeft() {
        api.movePlayer(new Movement(player.getId(), Player.Direction.RIGHT));
        api.movePlayer(new Movement(player.getId(), Player.Direction.LEFT));
        api.movePlayer(new Movement(player.getId(), Player.Direction.LEFT));
        Tile newTile = player.getTile();
        assertEquals(initialTile, newTile);
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
        assertEquals(TileType.PLAYER, initialTile.getType());
        Ticket ticket = TicketFactory.getTicket();
        Tile ticketTile = new Tile(initialTile.getX(), initialTile.getY() + 1);
        ticket.setTile(Optional.of(ticketTile));
        if (player.getDirection() != Player.Direction.DOWN) {
            api.movePlayer(new Movement(player.getId(), Player.Direction.DOWN));
        }
        api.pickUpTicket(new PlayerRequest(player.getId()));
        assertEquals(initialTile, player.getTile());
        assertEquals(TileType.PLAYER, initialTile.getType());
        assertEquals(ticket, player.getHeldTicket().get());
        assertEquals(TileType.EMPTY, ticketTile.getType());
    }

    @Test
    public void testPickUpTicket_valid() {
        testPickUpTicket(player);
    }

    @Test
    public void testPickUpTicket_noTicketPresent() {
        Tile noTicketTile = new Tile(initialTile.getX(), initialTile.getY() + 1);
        assertEquals(TileType.PLAYER, initialTile.getType());
        api.pickUpTicket(new PlayerRequest(player.getId()));
        assertEquals(initialTile, player.getTile());
        assertEquals(TileType.PLAYER, initialTile.getType());
        assertTrue(player.getHeldTicket().isEmpty());
        assertEquals(TileType.EMPTY, noTicketTile.getType());
    }

    @Test
    public void testPickUpTicket_wrongDirection() {
        assertEquals(TileType.PLAYER, initialTile.getType());
        Ticket ticket = TicketFactory.getTicket();
        Tile ticketTile = new Tile(initialTile.getX(), initialTile.getY() + 1);
        ticket.setTile(Optional.of(ticketTile));
        if (player.getDirection() != Player.Direction.UP) {
            api.movePlayer(new Movement(player.getId(), Player.Direction.UP));
        }
        api.pickUpTicket(new PlayerRequest(player.getId()));
        assertEquals(initialTile, player.getTile());
        assertEquals(TileType.PLAYER, initialTile.getType());
        assertTrue(player.getHeldTicket().isEmpty());
        assertEquals(TileType.EMPTY, new Tile(initialTile.getX(), initialTile.getY() - 1).getType());
        assertEquals(TileType.TICKET, ticketTile.getType());
    }

    @Test
    public void testDropTicket_valid() {
        this.testPickUpTicket(player);
        Ticket ticket = player.getHeldTicket().get();
        initialTile = player.getTile();
        api.dropTicket(new PlayerRequest(player.getId()));
        assertEquals(initialTile, player.getTile());
        assertEquals(TileType.PLAYER, initialTile.getType());
        assertTrue(player.getHeldTicket().isEmpty());
        Tile ticketTile = devastation.getBoard().getTileAt(initialTile.getX(), initialTile.getY() + 1);
        assertEquals(ticketTile, ticket.getTile().get());
        assertEquals(TileType.TICKET, ticketTile.getType());
    }


    @Test
    public void testDropTicket_noTicketPresent() {
        api.dropTicket(new PlayerRequest(player.getId()));
        assertEquals(initialTile, player.getTile());
        assertEquals(TileType.PLAYER, initialTile.getType());
        assertTrue(player.getHeldTicket().isEmpty());
        Tile noTicketTile = new Tile(initialTile.getX(), initialTile.getY() + 1);
        assertEquals(TileType.EMPTY, noTicketTile.getType());
    }
}