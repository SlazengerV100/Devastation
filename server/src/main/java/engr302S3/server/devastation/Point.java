package engr302S3.server.devastation;

import lombok.Getter;

/**
 * Coordinates of tiles in the devastation game
 */
@Getter
public class Point {
  private int x; // The x-coordinate of the point.
  private int y; // The y-coordinate of the point.

  /**
   * Constructor to initialize a point with x and y coordinates.
   *
   * @param x The x-coordinate.
   * @param y The y-coordinate.
   */
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
