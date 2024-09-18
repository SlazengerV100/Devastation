package engr302S3.server;

import engr302S3.server.ticketFactory.Task;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.ticketFactory.TicketFactory;
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
    //check each tile for a ticket, and update the ticket timer if there is one
    for(int y = 0; y<game.getBoardHeight();y++){
      for(int x = 0; x< game.getBoardWidth(); x++){
        if(game.getTileAt(x,y).getType() == TileType.TICKET){
          ((Ticket)game.getTileAt(x,y).getContent()).incrementTime();
        }
      }
    }
    //update the stations and tasks that they are working on
    for(Station station: game.getStations()){
      if(station.progress()){
        //broadcast an update to clients
      }
    }
  }

  /**
   * Every 20second try to generate a new ticket if there is room of the board
   */
  @Scheduled(fixedRate = 20000)
  public void createTicket(){
    for(int i = 0; i<game.getBoardHeight();i++){
      //check the first column of the board for generated tickets, I am assuming this is where we
      //will  spawn them
      Tile tile = game.getTileAt(0,i);
      if(tile.empty()){
        tile.setTicket(TicketFactory.getTicket());
        //broadcast an update to clients
        break;
      }
    }
  }
}
