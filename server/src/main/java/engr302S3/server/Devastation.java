package engr302S3.server;

import engr302S3.server.map.Board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

/**
 * Class used for initialising game board and managing game details
 */
@Component
@Getter
public class Devastation {

    private final Board board;

    @Setter private int score;      // Game score
    @Setter private boolean running;        // Flag to indicate if the game is running
    @Getter(AccessLevel.NONE) private int timeLeft = 300;           // Time left for the game (seconds)


    public Devastation() {
        this.board = new Board();
    }

    public int decreaseTime() {
        return timeLeft--;
    }
}