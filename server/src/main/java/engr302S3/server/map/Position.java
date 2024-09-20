package engr302S3.server.map;

import engr302S3.server.Devastation;

/**
 * Record for 2D position on a grid
 * @param x
 * @param y
 */
public record Position(int x, int y) {

    public Position {

        if (this.x() < 0 || this.x() > Board.BOARD_WIDTH || this.y() < 0 || this.y() > Board.BOARD_HEIGHT) {
            throw new IllegalArgumentException("Position x or y must be within range (" + Board.BOARD_WIDTH + ", " + Board.BOARD_HEIGHT + ")");
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
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}