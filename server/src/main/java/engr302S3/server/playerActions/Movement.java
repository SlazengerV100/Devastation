package engr302S3.server.playerActions;

import lombok.Getter;

@Getter
public class Movement {

    private String playerTitle;
    private String direction;

    public Movement() {}

    public Movement(String playerTitle, String direction) {
        this.playerTitle = playerTitle;
        this.direction = direction;
    }
}