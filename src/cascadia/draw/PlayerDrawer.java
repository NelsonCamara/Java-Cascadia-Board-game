package cascadia.draw;

import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import cascadia.board.Lot;
import cascadia.game.Player;
import cascadia.items.Tile;
import cascadia.utils.Position;


/**
 * Responsible for rendering a Player, including their name (fixed at the top),
 * current Lot (fixed at the bottom), and PlayerMap (moves with navigation).
 */
public class PlayerDrawer {
	
	
	


    /**
     * Draws a Player, including their name, current Lot, and PlayerMap.
     * The name and Lot are fixed on the screen, while the PlayerMap moves with navigation.
     *
     * @param graphics     the Graphics2D context to draw on.
     * @param player       the Player object to render.
     * @param translateX   the horizontal translation for navigation.
     * @param translateY   the vertical translation for navigation.
     * @param screenWidth  the width of the screen.
     * @param screenHeight the height of the screen.
     */
    public static void drawPlayer(Graphics2D graphics, Player player, int translateX, int translateY, int screenWidth, int screenHeight) {
        Objects.requireNonNull(player);
    		drawPlayerMap(graphics, player.getPlayerMap().getTuiles(), translateX, translateY);

        graphics.translate(-translateX, -translateY);

        drawPlayerName(graphics, player.getPlayerName(), screenWidth);

        player.getPlayerLot().ifPresent(lot -> drawPlayerLot(graphics, lot, screenWidth, screenHeight));

        graphics.translate(translateX, translateY);
    }

    /**
     * Draws the PlayerMap by delegating to the PlayerMapDrawer.
     *
     * @param graphics   the Graphics2D context to draw on.
     * @param tiles      the map of positions to tiles.
     * @param translateX the horizontal translation for navigation.
     * @param translateY the vertical translation for navigation.
     */
    public static void drawPlayerMap(Graphics2D graphics, Map<Position, Tile> tiles, int translateX, int translateY) {
    		PlayerMapDrawer.drawPlayerMap(graphics, tiles, translateX, translateY);
    }

    /**
     * Draws the Player's name at the top center of the screen (fixed).
     *
     * @param graphics    the Graphics2D context to draw on.
     * @param playerName  the name of the Player.
     * @param screenWidth the width of the screen.
     */
    public static void drawPlayerName(Graphics2D graphics, String playerName, int screenWidth) {
    		Objects.requireNonNull(playerName);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.setColor(Color.BLACK);
        int textWidth = graphics.getFontMetrics().stringWidth(playerName);
        int x = (screenWidth - textWidth) / 2; // Center the text horizontally
        int y = 30; // Fixed position at the top
        graphics.drawString(playerName, x, y);
    }

    /**
     * Draws the Player's current Lot at the bottom center of the screen (fixed).
     *
     * @param graphics     the Graphics2D context to draw on.
     * @param lot          the Lot to render.
     * @param screenWidth  the width of the screen.
     * @param screenHeight the height of the screen.
     */
    public static void drawPlayerLot(Graphics2D graphics, Lot lot, int screenWidth, int screenHeight) {
        int lotX = screenWidth / 2 - 50;
        int lotY = screenHeight - 150; 
        OptionsLotsDrawer.drawLot(graphics, lotX, lotY, lot);
    }
    
    
    
    /**
     * Draws fixed graphical elements associated with a player on the game screen.
     * This method renders the player's name at a fixed location and optionally displays the player's
     * lot if one is present. The graphical elements are drawn relative to the given screen dimensions.
     *
     * @param graphics    the {@link Graphics2D} context used for rendering
     * @param player      the {@link Player} whose elements are to be drawn
     * @param screenWidth the width of the game screen in pixels
     * @param screenHeight the height of the game screen in pixels
     * @throws NullPointerException if the graphics context or player is null
     */

    public static void drawFixedPlayerElements(Graphics2D graphics, Player player, int screenWidth, int screenHeight) {
    	Objects.requireNonNull(player);
      PlayerDrawer.drawPlayerName(graphics, player.getPlayerName(), screenWidth);

      player.getPlayerLot().ifPresent(lot -> PlayerDrawer.drawPlayerLot(graphics, lot, screenWidth, screenHeight));
    }
}
