package cascadia.game;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cascadia.items.Tile;
import cascadia.items.TileType;
import cascadia.utils.Position;

/**
 * Represents the score of a player in the game, including the scoring rules for different variants.
 */
public class Score {

    private static final int MAX_MAP_KEY = 6;
    private final Map<Integer, Integer> familyVariant = Map.of(
            1, 2,
            2, 5,
            3, 9,
            4, 9,
            5, 9,
            6, 9
    );
    private final Map<Integer, Integer> intermediateVariant = Map.of(
            1, 0,
            2, 5,
            3, 8,
            4, 12,
            5, 12,
            6, 12
    );
    private final boolean isfamilyVariant;
    private int actualScore;

    /**
     * Constructs a new Score with the specified game variant.
     *
     * @param isfamilyVariant true if the family variant is chosen, false otherwise
     */
    public Score(boolean isfamilyVariant) {
        this.isfamilyVariant = isfamilyVariant;
        this.actualScore = 0;
    }

    /**
     * Updates the score based on the groups of habitat tiles.
     *
     * @param groups a map of habitat types to lists of lists of habitat tiles
     * @throws NullPointerException if the groups map is null
     */
    public void updateScore(Map<TileType, List<List<Position>>> groups) {
        Objects.requireNonNull(groups);

        List<List<Position>> allTiles = groups.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
        if (isfamilyVariant) {
					updateMap(allTiles, familyVariant);
				} else {
					updateMap(allTiles, intermediateVariant);
				}
    }

    /**
     * Updates the score based on the list of lists of habitat tiles and the scoring map.
     *
     * @param allTiles the list of lists of habitat tiles
     * @param mapToUpdate the scoring map to use for updating the score
     */
    private void updateMap(List<List<Position>> allTiles, Map<Integer, Integer> mapToUpdate) {
        int maxScore = mapToUpdate.get(MAX_MAP_KEY);

        for (List<Position> lstTiles : allTiles) {
            if (mapToUpdate.containsKey(lstTiles.size())) {
							actualScore += mapToUpdate.get(lstTiles.size());
						} else {
                if (lstTiles.size() > MAX_MAP_KEY) {
									actualScore += maxScore;
								}
            }
        }
    }

    /**
     * Returns the scoring map for the family variant.
     *
     * @return the scoring map for the family variant
     */
    public Map<Integer, Integer> getFamilyVariant() {
        return familyVariant;
    }

    /**
     * Returns the scoring map for the intermediate variant.
     *
     * @return the scoring map for the intermediate variant
     */
    public Map<Integer, Integer> getIntermediateVariant() {
        return intermediateVariant;
    }

    /**
     * Returns a string representation of the actual score.
     *
     * @return a string representation of the actual score
     */
    @Override
    public String toString() {
        return "" + actualScore;
    }
    
    
    /**
     * Returns a int representation of the actual score.
     *
     * @return a int representation of the actual score
     */
    public int getActualScore() {
    	return actualScore;
    }
}
