package cascadia.board;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import cascadia.game.Player;
import cascadia.items.Tile;
import cascadia.utils.Position;

/**
 * Represents the game board with players, options lots, and a stack of tiles and tokens.
 */
public class Board {
    private final List<Player> players;
    private final OptionsLots opLots;
    private final Pioche stack;

    /**
     * Constructs a new Board with the specified players and game variant.
     *
     * @param nameFirstPlayer the name of the first player
     * @param nameSecondPlayer the name of the second player
     * @param isFamilyVariant true if the family variant is chosen, false otherwise
     * @throws NullPointerException if either player name is null
     */
    public Board(String nameFirstPlayer, String nameSecondPlayer, boolean isFamilyVariant) {
        Objects.requireNonNull(nameFirstPlayer);
        Objects.requireNonNull(nameSecondPlayer);
        this.players = new ArrayList<>(List.of(
                new Player(nameFirstPlayer, new PlayerMap(isFamilyVariant), Optional.empty()),
                new Player(nameSecondPlayer, new PlayerMap(isFamilyVariant), Optional.empty())
        ));
        this.stack = new Pioche(2);
        this.opLots = new OptionsLots(stack);
    }
    
    
    /**
     * Returns the scores of each player .
     *
     * @return string of scores.
     */
    public String finalScores() {
      return players.stream()
                    .map(Player::scoreAnnouncement) 
                    .collect(Collectors.joining("\n")); 
  }
    

    /**
     * Initializes the board by distributing tiles to the players.
     */
    public void initBoard() {
        for (Player player : players) {
            for (int i = 0; i < 3; i++) {
                Position currentPos = new Position(i, 0);
                Tile tile = stack.piocherHabitat();
                player.getPlayerMap().addTuile(tile,currentPos);
            }
        }
    }

    /**
     * Returns the list of players.
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the options lots.
     *
     * @return the options lots
     */
    public OptionsLots getOpLots() {
        return opLots;
    }

    /**
     * Returns the stack of tiles and token.
     *
     * @return the stack of tiles and tokens
     */
    public Pioche getStack() {
        return stack;
    }

    /**
     * Returns a string representation of the board.
     *
     * @return a string representation of the board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tuiles restantes :").append(stack.getPiocheTuiles().size()).append("\n");
        sb.append("Lots:\n").append(opLots.toString());
        players.forEach(player -> sb.append(player.toString()));
        return sb.toString();
    }
}
