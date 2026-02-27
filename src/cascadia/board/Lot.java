package cascadia.board;
import java.util.Optional;

import cascadia.items.Tile;
import cascadia.items.FaunaTokenType;

/**
 * Represents a lot consisting of a habitat type and a fauna token type.
 * A lot is immutable and holds the necessary information for the habitat type
 * and associated fauna token in the Cascadia game.
 *
 * @param typeHabitat The habitat type of this lot (e.g., FOREST, RIVER, MOUNTAIN).
 * @param typeToken The fauna token type associated with this lot (e.g., FOX, BEAR).
 */
public record Lot(Optional<Tile> tile, Optional<FaunaTokenType> typeToken) {


		public boolean isEmpty() {
			return tile.isEmpty() && typeToken.isEmpty();
		}





    @Override

    public String toString() {
    	return "Tuile Habitat: " +tile.toString()+" ||Jeton Faune :"+typeToken.toString()+"\n";
    }
}

