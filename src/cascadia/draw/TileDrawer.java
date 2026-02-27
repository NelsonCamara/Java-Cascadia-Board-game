package cascadia.draw;

import java.awt.*;
import java.util.Optional;

import cascadia.items.Tile;

/**
 * Responsible for rendering Tile objects.
 */
public class TileDrawer {

    /**
     * Draws a Tile on the provided Graphics2D context.
     *
     * @param graphics the Graphics2D context to draw on.
     * @param x        the X position of the Tile.
     * @param y        the Y position of the Tile.
     * @param tile     the Tile to render.
     */
    public static void drawTile(Graphics2D graphics, int x, int y, Tile tile) {
        Color tileColor = switch (tile.tileType()) {
            case FORET -> Color.GREEN;
            case RIVIERE -> Color.CYAN;
            case MONTAGNE -> Color.GRAY;
            case PRAIRIE -> Color.YELLOW;
            case MARAIS -> Color.DARK_GRAY;
        };

        graphics.setColor(tileColor);
        graphics.fillRect(x, y, 100, 100);
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawRect(x, y, 100, 100);

        graphics.setFont(new Font("Arial", Font.BOLD, 12));
        graphics.drawString(tile.tileType().toString(), x + 5, y + 15);

        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
        graphics.drawString(tile.firstTypeAccepted().toString(), x + 5, y + 35);
        graphics.drawString(tile.secondTypeAccepted().toString(), x + 5, y + 50);

        tile.faunaToken().ifPresent(token -> {
            graphics.setColor(Color.RED);
            graphics.fillOval(x + 40, y + 60, 20, 20);
            graphics.setFont(new Font("Arial", Font.BOLD, 10));
            graphics.setColor(Color.BLACK);
            graphics.drawString(token.tokenType().toString(), x + 30, y + 95);
        });
    }
    
    /**
     * Draws a red rectangle around a specified position to highlight a selectable element.
     * This method is used to visually indicate a selection or focus on a specific tile
     * by drawing a red border around it. The position can be adjusted using translation offsets.
     * @param graphics      the {@link Graphics2D} context used for rendering
     * @param pixelPosition an array containing the x and y pixel coordinates of the element to be highlighted
     * @param translateX    the x-axis offset applied to the position
     * @param translateY    the y-axis offset applied to the position
     * @throws NullPointerException if {@code graphics} or {@code pixelPosition} is null
     * @throws ArrayIndexOutOfBoundsException if {@code pixelPosition} does not contain at least two elements
     */

    public static void drawSelector(Graphics2D graphics, int[] pixelPosition, int translateX, int translateY) {
      int tileSize = 100; 
      int x = pixelPosition[0] + translateX;
      int y = pixelPosition[1] + translateY;

      graphics.setColor(Color.RED); 
      graphics.drawRect(x, y, tileSize, tileSize);
  }
}
