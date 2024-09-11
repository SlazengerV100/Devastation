package engr302S3.server;

public record Position(int x, int y) {

    /**
     * Adds a position to a current position
     *
     * @param position position to add
     * @return new position
     */
    public Position add(Position position) {
        return new Position(x + position.x(), y + position.y());
    }
}
