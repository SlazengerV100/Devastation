package engr302S3.server;

import engr302S3.server.ticketFactory.Task;
import engr302S3.server.ticketFactory.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class responsible for ensuring any objects that rely on time get updated accordingly.
 */
@Component
public class ScheduledTasks {
  private final Devastation game;

  /**
   * Injects the devastation component into this component
   * @param devastation
   */
  @Autowired
  public ScheduledTasks(Devastation devastation) {
    this.game = devastation;
  }

  /**
   * For every 1000 milliseconds that elapses, ensure that Spring Boot updates the game time,
   * Ticket age, and Task completion
   */
  @Scheduled(fixedRate = 1000)
  public void updateGameTime() {
    //update the game clock
    game.decreaseTime();
    for(Ticket ticket:game.getActiveTickets()){
      ticket.incrementTime();
      for(Task task:ticket.getTasks()){
        if(false){ //currently set to not do anything until Tasks are properly implemented
          task.updateCompletion();
        }
      }
    }

  }
}
