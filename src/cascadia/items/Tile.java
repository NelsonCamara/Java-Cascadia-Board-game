package cascadia.items;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import cascadia.utils.Position;

/**
 * Represents a habitat tile in the Cascadia game. Each tile has a habitat type, a position,
 * and may contain an optional fauna token.
 *
 * @param tileType the habitat type of the tile (e.g., FOREST, RIVER).
 * @param habitatPos the position of the tile on the player's map.
 * @param faunaToken an optional fauna token placed on this habitat tile.
 * @param firstTypeAccepted the first type of fauna token accepted by this tile.
 * @param secondTypeAccepted the second type of fauna token accepted by this tile.
 * @throws NullPointerException if any of the parameters are null.
 */
public record Tile(TileType tileType, Optional<FaunaToken> faunaToken, FaunaTokenType firstTypeAccepted, FaunaTokenType secondTypeAccepted) {

    /**
     * Constructor for the TuileHabitat record that verifies all parameters are non-null.
     */
    public Tile {
        Objects.requireNonNull(tileType);
        Objects.requireNonNull(faunaToken);
    }

    /**
     * Returns the type of the fauna token placed on this tile.
     *
     * @return the type of the fauna token
     * @throws NullPointerException if the token is not present
     */
    public FaunaTokenType getTokenType() {
        return faunaToken.get().tokenType();
    }

    /**
     * Checks if the tile is free, meaning it has no fauna token placed on it.
     *
     * @return true if the tile has no fauna token; false otherwise.
     */
    public boolean isFree() {
        return faunaToken.isEmpty();
    }



    /**
     * Checks if the specified fauna token type is accepted by this tile.
     *
     * @param token the fauna token type to check.
     * @return true if the token type is accepted; false otherwise.
     */
    public boolean isTokenAccepted(FaunaTokenType token) {
        return token.equals(firstTypeAccepted) || token.equals(secondTypeAccepted);
    }

    /**
     * Adds a fauna token to this tile if it is currently free. Returns a new instance of
     * TuileHabitat with the fauna token added. If the tile is already occupied, returns null.
     *
     * @param token the fauna token to add to this tile.
     * @return a new TuileHabitat with the fauna token added, or null if the tile is occupied.
     * @throws NullPointerException if the specified fauna token is null.
     */
    public Tile addToken(FaunaToken token) {
        Objects.requireNonNull(token);
        if (faunaToken.isEmpty()) {
            if (isTokenAccepted(token.tokenType())) {
                return new Tile(tileType, Optional.of(token), firstTypeAccepted, secondTypeAccepted);
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the habitat tile, showing its type and whether it is
     * occupied by a fauna token. If the tile is free, "EMPTY" is displayed instead of a token.
     *
     * @return a string representation of the habitat tile.
     */
    @Override
    public String toString() {
        if (isFree()) {
            return "(" + tileType.toString() + " | ACCEPTE " + firstTypeAccepted.toString() + " ET " + secondTypeAccepted.toString() + ") ";
        }
        return "(" + tileType.toString() + " | " + faunaToken.get().toString() + ") ";
    }

    /**
     * Returns a random fauna token type.
     *
     * @return a random fauna token type.
     */
    public static FaunaTokenType randomTokenType() {
        FaunaTokenType[] allTypes = FaunaTokenType.values();
        int randomIndex = new Random().nextInt(allTypes.length);
        return allTypes[randomIndex];
    }


}

