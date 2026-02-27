package cascadia.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import cascadia.items.Tile;

/**
 * Represents a position on a grid in the Cascadia game, defined by x and y coordinates.
 * Provides utility methods to check adjacency with other positions.
 *
 * @param x the x-coordinate of the position.
 * @param y the y-coordinate of the position.
 */
public record Position(int x, int y) {

    public static final Position NON_ASSIGNED = new Position(-24, -24);

    /**
     * Checks if this position is adjacent to another position. Two positions are
     * considered adjacent if they are exactly one unit apart horizontally or vertically.
     *
     * @param other the position to check adjacency with.
     * @return true if the positions are adjacent; false otherwise.
     * @throws NullPointerException if the other position is null.
     */
    public boolean isPosNextTo(Position other) {
        Objects.requireNonNull(other);
        return (x == other.x && Math.abs(y - other.y) == 1) ||
               (y == other.y && Math.abs(x - other.x) == 1);
    }

    /**
     * Checks if this position is the special non-assigned position.
     *
     * @return true if this position is the non-assigned position; false otherwise.
     */
    public boolean isNonAssigned() {
        return this.equals(NON_ASSIGNED);
    }

    /**
     * Prompts the user to enter a position by specifying x and y coordinates.
     *
     * @return the position entered by the user.
     */
    public static Position askPosition() {
        int scannedX, scannedY;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisir une position sur X :");
        scannedX = scanner.nextInt();
        System.out.println("Choisir une position sur Y :");
        scannedY = scanner.nextInt();
        return new Position(scannedX, scannedY);
    }
    
    
    /**
     * Extracts a set of valid positions around the current tiles, expanding the bounds by one unit in all directions.
     * This method identifies all positions that are either currently occupied by tiles or adjacent to them.
     * The result includes all positions within an expanded bounding box around the existing tiles.
     *
     * @param tiles a map where keys represent positions on a grid and values are tiles placed at those positions
     * @return a {@link Set} of {@link Position} objects representing valid positions, including adjacent unoccupied positions
     * @throws NullPointerException if {@code tiles} is null
     */

    public static Set<Position> extractValidPositions(Map<Position, Tile> tiles) {
      Set<Position> validPositions = new HashSet<>(tiles.keySet());

      int minX = tiles.keySet().stream().mapToInt(Position::x).min().orElse(0);
      int maxX = tiles.keySet().stream().mapToInt(Position::x).max().orElse(0);
      int minY = tiles.keySet().stream().mapToInt(Position::y).min().orElse(0);
      int maxY = tiles.keySet().stream().mapToInt(Position::y).max().orElse(0);

      minX -= 1;
      maxX += 1;
      minY -= 1;
      maxY += 1;

      for (int i = minX; i <= maxX; i++) {
          for (int j = minY; j <= maxY; j++) {
              Position position = new Position(i, j);
              if (!tiles.containsKey(position)) {
                  validPositions.add(position);
              }
          }
      }

      return validPositions;
  }
    
    
    
    /**
     * Checks if a position is adjacent to any position in the map.
     *
     * @param position the position to check.
     * @param tiles the map of existing tiles.
     * @return true if the position is adjacent, false otherwise.
     */
    public static boolean isPositionAdjacent(Position position, Map<Position, Tile> tiles) {
        for (Position existingPosition : tiles.keySet()) {
            if (existingPosition.isPosNextTo(position)) {
                return true;
            }
        }
        return false;
    }
}
