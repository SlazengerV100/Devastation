package engr302S3.server.players;

import engr302S3.server.ticketFactory.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class Developer extends Techie {

    public Developer(int x, int y, boolean active) {
        super(Role.DEVELOPER, x, y, active);
    }

}