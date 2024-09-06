package engr302S3.server.ticketFactory;

import engr302S3.server.StationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Responsible for generating Tickets and their associated Tasks
 */
public class TicketFactory {
  private static final List<String> ticketNames = Arrays.asList(
          "Bug Bash: Error Hunt",
          "Refactor the Spaghetti",
          "Optimize Algorithm Efficiency",
          "Database Query Overhaul",
          "UI Component Cleanup",
          "API Endpoint Enhancement",
          "Code Coverage Expansion",
          "Script Performance Tuning",
          "Dependency Version Upgrade",
          "Security Vulnerability Patch"
  );
  public static Ticket getTicket(){
    return new Ticket(generateTitle(), 0, generateBlowOutProb(), createTasks());
  }

  /**
   * Choose a random ticket name
   * @return ticketName
   */
  private static String generateTitle(){
    Random random = new Random();
    return ticketNames.get(random.nextInt(10));
  }

  /**
   * Generate a double that represents how likely a ticket will blow out into multiple new tickets.
   * Currently, the double represent a percentage, but can change to be decimal
   * @return double
   */
  private static double generateBlowOutProb(){
    Random random = new Random();
    return random.nextDouble(20);
  }


  /**
   * Generate a random arraylist of tasks
   * Minimum amount is one task for developer
   * Maximum amount is three tasks for developer and three tasks for tester
   * @return ArrayList
   */
  private static ArrayList<Task> createTasks(){
    ArrayList<Task> tasks = new ArrayList<>();
    int numStationTypesLeft = 6;

    //split stations by their designated rooms to make them easier to pick and choose depending on the generated random integer value
    ArrayList<StationType> stations = new ArrayList<>(List.of(StationType.values()));
    ArrayList<StationType> devStations = new ArrayList<>();
    ArrayList<StationType> testStations = new ArrayList<>(); //not used, but might find use later
    for(StationType station:stations){
      if(station.isTester()){
        testStations.add(station);
      }
      else{
        devStations.add(station);
      }
    }

    Random random = new Random();
    int taskAmount = random.nextInt(1,numStationTypesLeft+1); //the amount of tasks a ticket will have

    //there should always be at least one dev room task, so add a random dev station task first
    StationType station = devStations.get(random.nextInt(3));
    tasks.add(new Task(station));
    //remove the station from the list of valid stations
    numStationTypesLeft--;
    stations.remove(station);
    if(taskAmount == 1){ //if there is only 1 task for this ticket then return the array
      return tasks;
    }
    //if there are more than 1 assigned task to this ticket, then keep randomly selecting StationTypes
    for(int i = 1; i<taskAmount; i++){
      station = stations.get(random.nextInt(numStationTypesLeft));
      tasks.add(new Task(station));
      numStationTypesLeft--;
      stations.remove(station);
    }
    return tasks;

  }

}
