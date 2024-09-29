package engr302S3.server;

import engr302S3.server.map.Board;

import engr302S3.server.ticketFactory.Task;
import engr302S3.server.ticketFactory.Ticket;
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
    private ClientAPI clientAPI;


    public Devastation() {
        this.board = new Board();
        this.clientAPI = new ClientAPI(this);
    }

    public void decreaseTime() {
        timeLeft --;
    }

    /**
     * Update the game score when a ticket is completed
     * @param ticket completed ticket
     */
    public void updateScore(Ticket ticket){
        int scorePerTask = 50;
        int ticketScore = 0;
        int maxScore = 1000;

        // Update ticket score based on amount of tasks completed
        for (Task task : ticket.getTasks()) {
            if (task.getCompleted()) {
                ticketScore += scorePerTask;
            }
        }

        // Max ticket score can be 1000 and decreases to 0 based on time alive
        ticketScore = Math.max(maxScore - (ticket.getTotalTime()*5), 0);
        score += ticketScore;

        clientAPI.broadcastTicketResolve(ticket);
        clientAPI.broadcastScoreUpdate(score);
    }

}