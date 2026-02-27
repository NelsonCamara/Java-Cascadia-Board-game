package cascadia.draw;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import cascadia.board.Lot;
import cascadia.board.OptionsLots;
import cascadia.events.OptionsSelectionHandler;
import cascadia.items.FaunaTokenType;

/**
 * Responsible for rendering OptionsLots, which consist of multiple Lots.
 */
public class OptionsLotsDrawer {
	
	/**
     * Draws an OptionsLots, highlighting the selected and chosen Lots.
     *
     * @param graphics        the Graphics2D context to draw on.
     * @param x               the starting X position.
     * @param y               the starting Y position.
     * @param optionsLots     the OptionsLots to render.
     * @param selectionHandler manages the selection and chosen indices.
     */
    public static void drawOptionsLots(Graphics2D graphics, int x, int y, OptionsLots optionsLots, OptionsSelectionHandler selectionHandler) {
      Objects.requireNonNull(optionsLots);
      Objects.requireNonNull(selectionHandler);
    	int spacing = 150; 
        List<Lot> lots = optionsLots.getLots();

        for (int i = 0; i < lots.size(); i++) {
            if (i == selectionHandler.getSelectedIndex()) {
                drawHighlight(graphics, x + i * spacing, y, Color.YELLOW);
            }

            if (i == selectionHandler.getChosenIndex()) {
                drawHighlight(graphics, x + i * spacing, y, Color.GREEN);
            }

            drawLot(graphics, x + i * spacing, y, lots.get(i));
        }
    }

    /**
     * Draws a single Lot, consisting of a Tile and an optional token.
     *
     * @param graphics the Graphics2D context to draw on.
     * @param x        the X position of the Lot.
     * @param y        the Y position of the Lot.
     * @param lot      the Lot to render.
     */
    static void drawLot(Graphics2D graphics, int x, int y, Lot lot) {
        lot.tile().ifPresent(tile -> TileDrawer.drawTile(graphics, x, y, tile));
        lot.typeToken().ifPresent(tokenType -> drawIndependentToken(graphics, x, y + 120, tokenType));
    }

    /**
     * Draws a token that is not part of a Tile.
     *
     * @param graphics the Graphics2D context to draw on.
     * @param x        the X position of the token.
     * @param y        the Y position of the token.
     * @param tokenType the type of the token to render.
     */
    private static void drawIndependentToken(Graphics2D graphics, int x, int y, FaunaTokenType tokenType) {
        graphics.setColor(Color.RED);
        graphics.fillOval(x + 40, y, 20, 20);
        graphics.setFont(new Font("Arial", Font.BOLD, 10));
        graphics.setColor(Color.BLACK);
        graphics.drawString(tokenType.toString(), x + 30, y + 30);
    }

    /**
     * Draws a highlight rectangle around a Lot.
     *
     * @param graphics the Graphics2D context to draw on.
     * @param x        the X position of the highlight.
     * @param y        the Y position of the highlight.
     * @param color    the color of the highlight.
     */
    private static void drawHighlight(Graphics2D graphics, int x, int y, Color color) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(3));
        graphics.drawRect(x - 5, y - 5, 110, 150);
    }
    
    /**
     * Calls the method to draw the graphical representation of the available lots and their selection handler.
     * This method delegates the rendering of the {@link OptionsLots} to the {@link OptionsLotsDrawer} class,
     * positioning it at a predefined location on the screen. It also integrates the selection handling logic
     * for user interactions with the options.
     *
     * @param graphics         the {@link Graphics2D} context used for rendering
     * @param optionsLots      the {@link OptionsLots} object representing the current options to display
     * @param selectionHandler the {@link OptionsSelectionHandler} for managing user interactions with the options
     * @throws NullPointerException if any of the parameters is null
     */

  	public static void drawOptionsLotsCaller(Graphics2D graphics, OptionsLots optionsLots, OptionsSelectionHandler selectionHandler) {
  		Objects.requireNonNull(optionsLots);
  		Objects.requireNonNull(selectionHandler);
  		OptionsLotsDrawer.drawOptionsLots(
          graphics,
          50,
          400,
          optionsLots,
          selectionHandler
      );
    }
}
