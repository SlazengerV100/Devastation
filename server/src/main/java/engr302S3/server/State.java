package engr302S3.server;

import lombok.Getter;

@Getter
public class State {
    private int x;
    private int y;

    public State() {}

    public State(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

