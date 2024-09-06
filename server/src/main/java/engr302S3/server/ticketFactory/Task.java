package engr302S3.server.ticketFactory;

import engr302S3.server.StationType;
import lombok.Getter;

import java.util.Random;

/**
 * Tasks are the different objectives that must be completed within a ticket.
 * NOTE: May be re-designed to be a subclass of Ticket at a later date.
 */
@Getter
public class Task {
  private final String taskTitle;
  private final StationType stationType;
  private int timeToComplete; //2-10 seconds
  private Boolean completed = false;

  public Task(StationType stationType) {
    this.taskTitle = stationType.toString();
    Random random = new Random();
    this.timeToComplete = random.nextInt(2, 11);
    this.stationType = stationType;
  }

  /**
   * Reduce the time to completion, which should occur while this task is being worked at the relevant station
   */
  public void updateCompletion() {
    timeToComplete--;
    if (timeToComplete <= 0) {
      completed = true;
    }
  }
}
