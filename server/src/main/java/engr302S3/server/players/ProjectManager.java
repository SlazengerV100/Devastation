package engr302S3.server.players;

import java.util.Optional;

public class ProjectManager extends Player {

    public ProjectManager(int x, int y, boolean active) {
        super(Role.PROJECT_MANAGER, x, y, active);
    }
    public ProjectManager(boolean active) {
        super(Role.PROJECT_MANAGER,active);
    }

    @Override
    public void dropTicket() {
        if (this.getHeldTicket().isEmpty()) {
            return;
        }
        this.setHeldTicket(Optional.empty());
        //TODO
    }
}
