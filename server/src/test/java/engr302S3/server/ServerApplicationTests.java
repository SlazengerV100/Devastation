package engr302S3.server;

import engr302S3.server.players.Player;
import engr302S3.server.players.ProjectManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

	@Test
	public void testMovement1() {
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
	void contextLoads() {
	}

}
