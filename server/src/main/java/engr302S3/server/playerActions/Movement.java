package engr302S3.server.playerActions;

import lombok.Getter;

@Getter
public class Movement {

    private String role;
    private String direction;

    public Movement() {}

    public Movement(String role, String direction) {
        this.role = role;
        this.direction = direction;
    }
}