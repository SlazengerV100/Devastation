package engr302S3.server.players;

import engr302S3.server.map.Position;

/**
 * Class for developer player
 */
public class Developer extends Techie {

    public Developer(Position position) {
        super(Role.DEVELOPER, position);
    }
}