package engr302S3.server.map;

/**
 * Record for 2D position on a grid
 * @param x
 * @param y
 */
public record Position(int x, int y) {

    public Position {

        if (x < 0 || x > Board.BOARD_WIDTH || y < 0 || y > Board.BOARD_HEIGHT) {
            throw new IllegalArgumentException("Position x or y must be within range ( x = (0 ," + Board.BOARD_WIDTH + "), y = (0 ," + Board.BOARD_HEIGHT + ")");
        }
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}