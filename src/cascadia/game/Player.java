package cascadia.game;

import java.util.Objects;
import java.util.Optional;

import cascadia.board.Lot;
import cascadia.board.PlayerMap;


/**
 * Represents a player in the game, including their name, map, and optional lot.
 */
public class Player {

    private final String playerName;
    private final PlayerMap playerMap;
    private final Optional<Lot> playerLot;

    /**
     * Constructs a new Player with the specified name, player map, and optional lot.
     *
     * @param playerName the name of the player
     * @param playerMap the player's map in the game
     * @param playerLot an optional lot associated with the player
     */
    public Player(String playerName, PlayerMap playerMap, Optional<Lot> playerLot) {
    		Objects.requireNonNull(playerName);
    		Objects.requireNonNull(playerMap);
        this.playerName = playerName;
        this.playerMap = playerMap;
        this.playerLot = playerLot;
    }
    
    /**
     * Returns the name of the player with the score.
     *
     * @return the string of the name of the player with the score
     */
    public String scoreAnnouncement() {
    	return "Le score de "+playerName+" est :"+playerMap.getScore().toString()+ "!\n";
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the player's map.
     *
     * @return the player's map
     */
    public PlayerMap getPlayerMap() {
        return playerMap;
    }

    /**
     * Returns the optional lot associated with the player.
     *
     * @return the optional lot associated with the player
     */
    public Optional<Lot> getPlayerLot() {
        return playerLot;
    }

    /**
     * Returns a new Player instance with an updated playerLot.
     *
     * @param lot the new player lot
     * @return a new Player instance with the updated playerLot
     */
    public Player updatedPlayerLot(Optional<Lot> lot) {
        return new Player(this.playerName, this.playerMap, lot);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
					return true;
				}
        if (o == null || getClass() != o.getClass()) {
					return false;
				}

        Player player = (Player) o;

        if (!playerName.equals(player.playerName) || !playerMap.equals(player.playerMap)) {
					return false;
				}
        return playerLot.equals(player.playerLot);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = playerName.hashCode();
        result = 31 * result + playerMap.hashCode();
        result = 31 * result + playerLot.hashCode();
        return result;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return a string representation of the player
     */
    @Override
    public String toString() {
        return "Nom Joueur :" + playerName + " \nEnvironnement:\n" + playerMap.toString() + "\nLot:" + playerLot.toString() + "\n";
    }
}

