package cascadia.board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cascadia.game.Score;
import cascadia.game.TuilesGroups;
import cascadia.items.FaunaToken;
import cascadia.items.Tile;
import cascadia.items.FaunaTokenType;
import cascadia.utils.Position;

/**
 * Represents the map of a player in the game, including habitat tiles, placed fauna tokens, and scoring information.
 */
public class PlayerMap {

    private final Map<Position, Tile> tuiles = new HashMap<>();
    private final TuilesGroups groups = new TuilesGroups();
    private final Score playerMapScore;

    /**
     * Constructs a new PlayerMap with the specified game variant.
     *
     * @param isFamilyVariant true if the family variant is chosen, false otherwise
     */
    public PlayerMap(boolean isFamilyVariant) {
        this.playerMapScore = new Score(isFamilyVariant);
    }

    /**
     * Returns the current score of the player's map.
     *
     * @return the current score of the player's map
     */
    public Score getScore() {
    		playerMapScore.updateScore(TuilesGroups.calculateGroups(tuiles));
        return playerMapScore;
    }

    /**
     * Adds a habitat tile to the player's map if the position is not already occupied.
     *
     * @param tuile the habitat tile to be added to the map
     * @return true if the tile was successfully added; false if the position is already occupied
     * @throws NullPointerException if the specified tile is null
     */
    public boolean addTuile(Tile tuile,Position pos) {
    		Objects.requireNonNull(pos);
        Objects.requireNonNull(tuile);
        if (!tuiles.containsKey(pos)) {
            tuiles.put(pos, tuile);
            return true;
        }
        return false;
    }

    /**
     * Places a fauna token on a specific habitat tile at a given position, if the position has a tile and can accept the token.
     * The tile is updated to reflect the token placement, and the token is added to the list of placed tokens.
     *
     * @param token the fauna token to be placed on the habitat tile
     * @param pos the position where the token will be placed
     * @return true if the token was successfully placed; false otherwise
     * @throws NullPointerException if the token or position is null
     */
    public boolean putToken(FaunaToken token, Position pos) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(pos);

        if (tuiles.containsKey(pos)) {
            Tile newTuile = tuiles.get(pos).addToken(token);
            if (newTuile != null) {
                tuiles.put(pos, newTuile);
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a list of map positions arranged in a grid format, used to create the initial map structure.
     *
     * @param size the size of the grid (width and height)
     * @return a list of positions corresponding to a grid of the specified size
     */
    private static List<Position> generateMapPositions(int size) {
        List<Position> lstPos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                lstPos.add(new Position(j, i));
            }
        }
        return lstPos;
    }

    /**
     * Returns a string representation of the player's map. Displays each position on the map grid, showing the habitat
     * type and fauna token at each location, or "(EMPTY | EMPTY)" if the position is unoccupied.
     *
     * @return a string representation of the map in grid format
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("GROUPS: ").append(groups.toString()).append("\n");
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 23; j++) {
                Position tmp = new Position(j, i);
                if (tuiles.containsKey(tmp)) {
                    sb.append(tuiles.get(tmp));
                } else {
                    sb.append("(EMPTY | EMPTY) ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the map of habitat tiles.
     *
     * @return the map of habitat tiles
     */
    public Map<Position, Tile> getTuiles() {
        return tuiles;
    }

  

    /**
     * Returns a set of valid positions where a fauna token of the specified type can be placed.
     *
     * @param tokenType the type of the fauna token
     * @return a set of valid positions for the specified fauna token type
     * @throws NullPointerException if the token type is null
     */
    public Set<Position> validTokensPositions(FaunaTokenType tokenType) {
        Objects.requireNonNull(tokenType);

        return tuiles.entrySet().stream()
                .filter(entry -> entry.getValue().isFree() && entry.getValue().isTokenAccepted(tokenType))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
