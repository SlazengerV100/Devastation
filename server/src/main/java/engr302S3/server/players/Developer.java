package engr302S3.server.players;

import engr302S3.server.Position;

public class Developer extends Techie {

    public Developer(Position position, boolean active) {
        super(Role.DEVELOPER, position, active);
    }
}