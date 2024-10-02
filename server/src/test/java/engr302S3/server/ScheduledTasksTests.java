package engr302S3.server;

import engr302S3.server.map.Board;
import engr302S3.server.map.Station;
import engr302S3.server.ticketFactory.Ticket;
import engr302S3.server.playerActions.TaskProgressBroadcast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduledTasksTests {

    private ScheduledTasks scheduledTasks;
    private ClientAPI clientAPI;
    private Board board;
    private Station station;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        clientAPI = mock(ClientAPI.class);
        board = mock(Board.class);
        station = mock(Station.class);
        ticket = mock(Ticket.class);

        // Mock the internal structure of ClientAPI and Board
        when(clientAPI.getDevastation()).thenReturn(mock(Devastation.class));
        when(clientAPI.getDevastation().getBoard()).thenReturn(board);

        scheduledTasks = new ScheduledTasks(clientAPI);
    }

    @Test
    public void testUpdateGameTime_updatesTimer() {
        when(clientAPI.getDevastation().decreaseTime()).thenReturn(1000);

        scheduledTasks.updateGameTime();

        // Verify that the broadcastTimerUpdate is called with the correct value
        verify(clientAPI).broadcastTimerUpdate(1000);
    }

    @Test
    public void testUpdateGameTime_updatesTicketTime() {
        Map<Long, Ticket> tickets = new HashMap<>();
        tickets.put(ticket.getId(), ticket);

        when(board.getTickets()).thenReturn(tickets);

        scheduledTasks.updateGameTime();

        // Verify that the ticket's incrementTime() is called
        verify(ticket).incrementTime();
    }

    @Test
    public void testUpdateGameTime_updatesStationProgress_andBroadcastTaskCompletion() {
        Map<Long, Station> stations = new HashMap<>();
        stations.put(station.getId(), station);

        when(board.getStations()).thenReturn(stations);
        when(station.progress()).thenReturn(true);
        when(station.getTicketWorkingOn()).thenReturn(Optional.of(ticket));
//        when(ticket.getStationType()).thenReturn("type");

        scheduledTasks.updateGameTime();

        // Verify that the station's progress() is called
        verify(station).progress();

        // Verify that broadcastTaskCompletion is called
        verify(clientAPI).broadcastTaskCompletion(any(TaskProgressBroadcast.class));
    }

    @Test
    public void testUpdateGameTime_noTaskCompletionBroadcast_whenStationNotProgressed() {
        HashMap<Long, Station> stations = new HashMap<>();
        stations.put(station.getId(), station);

        when(board.getStations()).thenReturn(stations);
        when(station.progress()).thenReturn(false);

        scheduledTasks.updateGameTime();

        // Verify that broadcastTaskCompletion is NOT called when station progress is false
        verify(clientAPI, never()).broadcastTaskCompletion(any(TaskProgressBroadcast.class));
    }

    @Test
    public void testUpdateGameTime_ticketAbsentFromStation() {
        HashMap<Long, Station> stations = new HashMap<>();
        stations.put(station.getId(), station);

        when(board.getStations()).thenReturn(stations);
        when(station.progress()).thenReturn(false);
        when(station.getTicketWorkingOn()).thenReturn(Optional.empty());

        scheduledTasks.updateGameTime();

        // Verify that broadcastTaskCompletion is not called when no ticket is present
        verify(clientAPI, never()).broadcastTaskCompletion(any(TaskProgressBroadcast.class));
    }

    @Test
    public void testUpdateGameTime_orderOfOperations() {
        HashMap<Long, Station> stations = new HashMap<>();
        stations.put(station.getId(), station);

        HashMap<Long, Ticket> tickets = new HashMap<>();
        tickets.put(ticket.getId(), ticket);

        when(board.getStations()).thenReturn(stations);
        when(board.getTickets()).thenReturn(tickets);

        InOrder inOrder = Mockito.inOrder(clientAPI, station, ticket);

        scheduledTasks.updateGameTime();

        // Verify the order of operations
        inOrder.verify(clientAPI).broadcastTimerUpdate(anyInt());
        inOrder.verify(ticket).incrementTime();
        inOrder.verify(station).progress();
    }
}

