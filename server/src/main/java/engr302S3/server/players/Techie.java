package engr302S3.server.players;

import engr302S3.server.Position;
import engr302S3.server.ticketFactory.Ticket;

import java.util.Optional;

public abstract class Techie extends Player {

    private int burnout;
    private final Ticket[] ticketsInAreaArray;
    private int ticketsInArea;
    private boolean disabled;

    public Techie(Role role, Position position, boolean active) {
        super(role, position);
        this.ticketsInAreaArray = new Ticket[10];
        this.ticketsInArea = 0;
        this.disabled = false;
    }

    /**
     * Increase burnout dependent on amount of tickets in the area
     */
    public void incrementBurnout() {
        if (ticketsInArea == ticketsInAreaArray.length) {
            disabled = true;
            ticketsInArea = 0;
            for (int i = 0; i < ticketsInAreaArray.length; i++) {
                ticketsInAreaArray[i] = null;
            }
            //SOME FORM OF SLEEP
        } else if (burnout == 100) {
            disabled = true;
            burnout = 0;
            //SOME FORM OF SLEEP
        }
        this.burnout++;
    }

    /**
     * Add ticket to tickets in area array and increment total amount of tickets
     * @param ticket to add to array
     */
    public void addTicketToArea(Ticket ticket) {
        ticketsInAreaArray[ticketsInArea] = ticket;
        ticketsInArea++;
    }

    /**
     * This should be some form of data that prints tasks in area to the screen
     * or can be deleted if we want another form of implementation, we could just store
     * tickets in area as an int and do another way.
     */
    public void getTicketsInformation() {
        //TODO
    }

    /**
     * Implement station dependent logic
     */
    @Override
    public void dropTicket() {
        if (this.getHeldTicket().isEmpty()) {
            return;
        }
        this.setHeldTicket(Optional.empty());
        //TODO
    }
}
