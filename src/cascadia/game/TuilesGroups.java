package cascadia.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import cascadia.items.Tile;
import cascadia.items.TileType;
import cascadia.utils.Position;

/**
 * Represents the groups of habitat tiles in the game, managing the merging and updating of these groups.
 */
public class TuilesGroups {

    private final Map<TileType, List<List<Position>>> groups = new HashMap<>();

    /**
     * Calculate groups of adjacent tiles of the same habitat type.
     *
     * This method processes a map of tiles to identify connected groups of tiles
     * that share the same habitat type. It returns a map where the keys are the habitat
     * types, and the values are lists of groups, with each group represented as a
     * list of positions.
     *
     * @param tuiles a map where keys are positions and values are habitat tiles
     * @return a map of habitat types to lists of connected tile groups
     * @throws NullPointerException if the input map is null
     */
    public static Map<TileType, List<List<Position>>> calculateGroups(Map<Position, Tile> tuiles) {
        Objects.requireNonNull(tuiles, "The map of tiles cannot be null.");

        Map<TileType, List<List<Position>>> groupedTiles = new HashMap<>();
        Set<Position> visited = new HashSet<>();

        for (Map.Entry<Position, Tile> entry : tuiles.entrySet()) {
            Position pos = entry.getKey();
            Tile tuile = entry.getValue();

            if (!visited.contains(pos)) {
                List<Position> group = new ArrayList<>();
                dfs(pos, tuile.tileType(), tuiles, visited, group);
               
                groupedTiles.putIfAbsent(tuile.tileType(), new ArrayList<>());
                groupedTiles.get(tuile.tileType()).add(group);
            }
        }

        return groupedTiles;
    }

    /**
     * dfs to find all connected tiles of the same habitat type.
     *
     * This method explores all adjacent positions recursively to identify connected tiles of
     * the specified habitat type. It marks visited tiles to avoid processing them multiple times.
     *
     * @param current the current position being explored
     * @param habitatType the habitat type to match
     * @param tuiles the map of positions to habitat tiles
     * @param visited a set of positions already visited
     * @param group the list of positions forming the current group
     */
    private static void dfs(Position current, TileType habitatType, Map<Position, Tile> tuiles,
                            Set<Position> visited, List<Position> group) {
        if (visited.contains(current)) {
            return;
        }

        Tile currentTile = tuiles.get(current);
        if (currentTile == null || !currentTile.tileType().equals(habitatType)) {
            return;
        }

        visited.add(current);
        group.add(current);

        // Recursively explore neighboring positions
        for (Position neighbor : getNeighbors(current)) {
            dfs(neighbor, habitatType, tuiles, visited, group);
        }
    }

    /**
     * Get the list of neighboring positions adjacent to a given position.
     *
     * This method generates a list of positions that are directly adjacent
     * (horizontally or vertically) to the specified position.
     *
     * @param position the position for which neighbors are calculated
     * @return a list of positions adjacent to the given position
     */
    private static List<Position> getNeighbors(Position position) {
        int x = position.x();
        int y = position.y();
        return List.of(
            new Position(x + 1, y),
            new Position(x - 1, y),
            new Position(x, y + 1),
            new Position(x, y - 1)
        );
    }

    @Override
    public String toString() {
        return groups.toString();
    }

		public Map<TileType, List<List<Position>>> getGroups() {
			return groups;
		}
}
