package cascadia.board;

import java.util.Collections;
import java.util.Optional;
import java.util.Stack;

import cascadia.items.Tile;
import cascadia.items.TileType;
import cascadia.items.FaunaTokenType;
import cascadia.utils.Position;

/**
 * Represents the draw pile (Pioche) of tiles and fauna tokens in the Cascadia game.
 * It handles the initialization, drawing, and recycling of tiles and tokens.
 */
public class Pioche {

    private final Stack<Tile> tuiles;
    private final Stack<FaunaTokenType> tokens;
    private final int nbPlayers;

    /**
     * Constructor that initializes the draw pile based on the number of players.
     * Populates the pile with habitat tiles and fauna tokens.
     *
     * @param nbJoueurs the number of players in the game. Must be at least 1.
     * @throws IllegalArgumentException if the number of players is less than 1.
     */
    public Pioche(int nbJoueurs) {
        this.tuiles = new Stack<>();
        this.tokens = new Stack<>();
        this.nbPlayers = nbJoueurs;

        if (nbJoueurs < 1) {
            throw new IllegalArgumentException("The number of players must be at least 1.");
        }

        for (TileType typeH : TileType.values()) {
            for (int i = 0; i < 17; i++) {
            	 	FaunaTokenType firstTypeTokenAccepted,secondTypeTokenAccepted;
            	 	firstTypeTokenAccepted = Tile.randomTokenType();
            	 	secondTypeTokenAccepted = Tile.randomTokenType();
            	 	while(firstTypeTokenAccepted.equals(secondTypeTokenAccepted)) {
									secondTypeTokenAccepted = Tile.randomTokenType();
								}

                tuiles.add(new Tile(typeH,Optional.empty(),firstTypeTokenAccepted, secondTypeTokenAccepted));
            }
        }

        for (FaunaTokenType typeJ : FaunaTokenType.values()) {
            for (int i = 0; i < 20; i++) {
                tokens.add(typeJ);
            }
        }

        Collections.shuffle(tuiles);
        Collections.shuffle(tokens);

        for (int i = 0; i < ((20 * nbJoueurs) + 3); i++) {
            tuiles.removeFirst();
        }
    }

    /**
     * Returns the stack of habitat tiles in the draw pile.
     *
     * @return the stack of habitat tiles.
     */
    public Stack<Tile> getPiocheTuiles() {
        return tuiles;
    }

    /**
     * Returns the stack of fauna tokens in the draw pile.
     *
     * @return the stack of fauna tokens.
     */
    public Stack<FaunaTokenType> getPiocheJetons() {
        return tokens;
    }

    /**
     * Draws a fauna token from the top of the stack.
     *
     * @return the drawn fauna token.
     * @throws IllegalStateException if there are no tokens left to draw.
     */
    public FaunaTokenType piocherToken() {
        if (!tokens.isEmpty()) {
            return tokens.pop();
        }
        throw new IllegalStateException("No more tokens available to draw.");
    }

    /**
     * Draws a habitat tile from the top of the stack.
     *
     * @return the drawn habitat tile.
     * @throws IllegalStateException if there are no habitat tiles left.
     */
    public Tile piocherHabitat() {
        if (!tuiles.isEmpty()) {
            return tuiles.pop();
        }
        throw new IllegalStateException("No more habitat tiles available");
    }

    /**
     * Recycles a fauna token back into the draw pile and shuffles the pile.
     *
     * @param typeToken the fauna token to be recycled back into the pile.
     */
    public void recyclerToken(FaunaTokenType typeToken) {
        tokens.push(typeToken);
        Collections.shuffle(tokens);
    }
}