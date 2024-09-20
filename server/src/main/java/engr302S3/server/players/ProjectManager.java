package engr302S3.server.players;

import engr302S3.server.map.Position;

/**
 * Class for project manager player
 */
public class ProjectManager extends Player {

    public ProjectManager(Position position) {
        super(Role.PROJECT_MANAGER, position);
    }
}