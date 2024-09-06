package engr302S3.server.players;

import engr302S3.server.ticketFactory.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class Tester extends Techie {

    public Tester(int x, int y, boolean active) {
        super(Role.TESTER, x, y, active);
    }

}