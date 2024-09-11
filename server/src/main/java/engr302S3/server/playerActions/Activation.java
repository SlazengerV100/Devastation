package engr302S3.server.playerActions;

import lombok.Getter;

@Getter
public class Activation {

    private String playerTitle;
    private boolean activate;

    public Activation() {}

    public Activation(String playerTitle, boolean activate) {
        this.playerTitle = playerTitle;
        this.activate = activate;
    }
}
