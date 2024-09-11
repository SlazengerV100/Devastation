package engr302S3.server;

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
		Player player = new ProjectManager(new Position(1,1), true);

		assert new Position(1, 1).equals(player.getPosition());

		player.movePlayer(Player.Direction.RIGHT);

		assert new Position(2, 1).equals(player.getPosition());

		player.movePlayer(Player.Direction.LEFT);
		player.movePlayer(Player.Direction.LEFT);
		try {
			player.movePlayer(Player.Direction.LEFT);
		} catch (IllegalArgumentException e) {
			System.out.println("Success");
		}
	}

	@Test
	public void testTicketFactory() {
		TicketFactory ticketFactory = new TicketFactory();
		List<Ticket> tickets = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			tickets.add(ticketFactory.getTicket());
		}

		tickets.forEach(e -> {
			System.out.println(e.getTasks().size());
		});

		assert tickets.stream().anyMatch(e -> e.getTasks().size() == 1);
		assert tickets.stream().anyMatch(e -> e.getTasks().size() == StationType.values().length);
	}

	@Test
	void contextLoads() {
	}

}
