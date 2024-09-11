package engr302S3.server;

public record Position(int x, int y) {

    //Values not final
    private static final int minX = 0;
    private static final int minY = 0;
    private static final int maxX = 50;
    private static final int maxY = 50;

    public Position {

        if (this.x() < minX || this.x() > maxX || this.y() < minY || this.y() > maxY) {
            throw new IllegalArgumentException("Position x or y must be within range (" + minX + ", " + maxX + ")");
        }
    }

    /**
     * Adds a position to a current position
     *
     * @param position position to add
     * @return new position
     */
    public Position add(Position position) {
        return new Position(x + position.x(), y + position.y());
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}