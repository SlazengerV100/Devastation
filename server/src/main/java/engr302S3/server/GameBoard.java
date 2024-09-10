package engr302S3.server;

import engr302S3.server.players.Player;
import lombok.Getter;

import java.util.Map;

@Getter
public class GameBoard {
    private final Tile[][] gameBoard;

    public GameBoard(Tile[][] gameBoard) {
        this.gameBoard = gameBoard;
    }
}
