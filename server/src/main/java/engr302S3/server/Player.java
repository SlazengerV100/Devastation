package engr302S3.server;

import lombok.Getter;

@Getter
public class Player {

    // Enum for role
    public enum Role {
        PROJECT_MANAGER,
        DEVELOPER,
        TESTER
    }

    // Getters and Setters for role, x, y, and active
    // Fields to store the role, position, and active status of the player
    private Role role;
    private int x;
    private int y;
    private boolean active;

    // Constructor
    public Player(Role role, int x, int y, boolean active) {
        this.role = role;
        this.x = x;
        this.y = y;
        this.active = active;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Player role: " + role + ", Position: (" + x + ", " + y + "), Active: " + active;
    }
}

