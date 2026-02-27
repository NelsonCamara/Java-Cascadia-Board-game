package cascadia.draw;
import java.awt.*;
import java.util.Map;

import cascadia.items.Tile;
import cascadia.utils.Position;

import java.awt.*;
import java.util.Map;

/**
 * Responsible for rendering the PlayerMap, including empty slots for future tiles.
 */
public class PlayerMapDrawer {

    private static final int TILE_SIZE = 100; 
    private static final int SPACING = 110;  

    /**
     * Draws the tiles of the PlayerMap, along with empty slots for future tiles.
     *
     * @param graphics   the Graphics2D context to draw on.
     * @param tiles      the map of positions to tiles.
     * @param translateX the horizontal translation for navigation.
     * @param translateY the vertical translation for navigation.
     */
    public static void drawPlayerMap(Graphics2D graphics, Map<Position, Tile> tiles, int translateX, int translateY) {
        // Determine bounds to create a grid of slots
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
                int x = i * SPACING + translateX;
                int y = j * SPACING + translateY;

                if (tiles.containsKey(position)) {
                    TileDrawer.drawTile(graphics, x, y, tiles.get(position));
                } else {
                    drawEmptySlot(graphics, x, y);
                }
            }
        }
    }

    /**
     * Draws an empty slot to indicate a position where a tile can be placed.
     *
     * @param graphics the Graphics2D context to draw on.
     * @param x        the X position of the slot.
     * @param y        the Y position of the slot.
     */
    private static void drawEmptySlot(Graphics2D graphics, int x, int y) {
        graphics.setColor(Color.LIGHT_GRAY); 
        graphics.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        graphics.setColor(Color.DARK_GRAY); 
        graphics.drawRect(x, y, TILE_SIZE, TILE_SIZE);
    }
}
