package cascadia.board;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import cascadia.game.Player;
import cascadia.items.Tile;
import cascadia.items.FaunaTokenType;

/**
 * Represents a set of 4 lots in the Cascadia game, where each lot consists of a habitat and a fauna token.
 * Provides functionalities to initialize, assign, and manage lots for players during the game.
 */
public class OptionsLots {

    private final List<Lot> lots;

    /**
     * Constructor that initializes the lots using the provided Pioche.
     *
     * @param pioche the draw pile from which lots are created. It must not be null.
     * @throws NullPointerException if the provided pioche is null.
     */
    public OptionsLots(Pioche pioche) {
        Objects.requireNonNull(pioche);
        this.lots = new ArrayList<>();
        initLots(pioche);
    }

    /**
     * Returns an unmodifiable view of the lots.
     *
     * @return a list of lots that cannot be modified.
     */
    public List<Lot> getLots() {
        return Collections.unmodifiableList(lots);
    }

    /**
     * Initializes the lots by drawing from the provided Pioche.
     *
     * @param pioche the draw pile to be used for creating the lots.
     * @throws NullPointerException if the provided pioche is null.
     */
    private void initLots(Pioche pioche) {
        Objects.requireNonNull(pioche);
        for (int i = 0; i < 4; i++) {
            Lot tmp = new Lot(Optional.of(pioche.piocherHabitat()), Optional.of(pioche.piocherToken()));
            lots.add(tmp);
        }
    }

    /**
     * Creates a new Lot by drawing from the Pioche.
     *
     * @param pioche the draw pile from which the new Lot will be created.
     * @return a new Lot with a habitat and a fauna token drawn from the pioche.
     * @throws NullPointerException if the provided pioche is null.
     */
    private Lot newLot(Pioche pioche) {
        Objects.requireNonNull(pioche);
        return new Lot(Optional.of(pioche.piocherHabitat()), Optional.of(pioche.piocherToken()));
    }

    /**
     * Assigns a Lot to a player if they do not already have one. The selected Lot is removed from the options,
     * and a new Lot is added to the list from the Pioche.
     *
     * @param lot the lot to be assigned to the player.
     * @param pioche the draw pile to replenish the lot options.
     * @param player the player who will receive the lot.
     * @return the updated Player instance with the new lot assigned.
     * @throws IllegalArgumentException if the lot is not in the list of available lots.
     * @throws NullPointerException if any of the parameters are null.
     */
    public Player givePlayerLot(Lot lot, Pioche pioche, Player player) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(pioche);
        Objects.requireNonNull(lot);

        if (!lots.contains(lot)) {
            throw new IllegalArgumentException("This lot is not in the list of available lots!");
        }

        if (player.getPlayerLot().isEmpty() || player.getPlayerLot().get().isEmpty()) {
            Player updatedPlayer = player.updatedPlayerLot(Optional.of(lot));
            lots.remove(lot);
            lots.add(newLot(pioche));
            return updatedPlayer;
        } else {
            System.out.println(player.getPlayerName() + " already has a lot.");
            
        }
        return player;
    }

    /**
     * Discards a fauna token from a specified lot and recycles it into the pioche.
     *
     * @param indexLotToken the index of the lot from which to discard the token.
     * @param pioche the draw pile where the token will be recycled.
     * @throws IllegalArgumentException if the index is out of range (0-3).
     * @throws NullPointerException if the pioche is null.
     */
    public void discardToken(int indexLotToken, Pioche pioche) {
        Objects.requireNonNull(pioche);
        if (indexLotToken < 0 || indexLotToken > 3) {
            System.out.println("The index must be between 0 and 3.");
        } else {
            pioche.recyclerToken(lots.get(indexLotToken).typeToken().get());
        }
    }

    /**
     * Discards multiple fauna tokens from the specified lots and recycles them into the pioche.
     *
     * @param indexLotsTokens a list of indices corresponding to the lots whose tokens will be discarded.
     * @param pioche the draw pile where the tokens will be recycled.
     * @throws NullPointerException if the pioche is null.
     */
    public void discardMultipleTokens(List<Integer> indexLotsTokens, Pioche pioche) {
        Objects.requireNonNull(pioche);
        for (int elem : indexLotsTokens) {
            discardToken(elem, pioche);
        }
    }

    /**
     * Allows a player to choose a token and a tile from the available lots and assigns them to the player.
     * The token and habitat in the lots are then updated accordingly.
     *
     * @param indexLotToken the index of the lot from which to choose the fauna token.
     * @param indexLotTuile the index of the lot from which to choose the habitat tile.
     * @param player the player making the choice.
     * @return the updated Player instance with the new lot assigned.
     * @throws IllegalArgumentException if either index is out of range (0-3).
     * @throws NullPointerException if the player is null.
     */
    public Player chooseTokenAndTile(int indexLotToken, int indexLotTuile, Player player) {
        Objects.requireNonNull(player);
        if (indexLotToken < 0 || indexLotToken > 3 || indexLotTuile < 0 || indexLotTuile > 3) {
            System.out.println("The index must be between 0 and 3.");
            return player;
        } else {
            Tile tmpTile = lots.get(indexLotTuile).tile().get();
            FaunaTokenType tmpTypeToken = lots.get(indexLotToken).typeToken().get();

            Player updatedPlayer = player.updatedPlayerLot(Optional.of(new Lot(Optional.of(tmpTile), Optional.of(tmpTypeToken))));

            lots.set(indexLotTuile, new Lot(Optional.empty(), Optional.of(tmpTypeToken)));
            lots.set(indexLotToken, new Lot(Optional.of(tmpTile), Optional.empty()));

            return updatedPlayer;
        }
    }

    /**
     * Returns a string representation of the lots.
     *
     * @return a string representation of the lots.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> optionsStrNames = List.of("Lot 0:\n", "Lot 1:\n", "Lot 2:\n", "Lot 3:\n");
        for (int i = 0; i < 4; i++) {
            sb.append(optionsStrNames.get(i)).append(lots.get(i));
        }
        return sb.toString();
    }

    /**
     * Identifies the indices of lots that have a surplus of a specific fauna token.
     *
     * @return a set of indices of lots with a surplus of a specific fauna token.
     */
    private Set<Integer> surpopIndexes() {
        Map<FaunaTokenType, Set<Integer>> mapIndexes = new HashMap<>();
        for (int i = 0; i < lots.size(); i++) {
            FaunaTokenType type = lots.get(i).typeToken().get();
            mapIndexes.computeIfAbsent(type, k -> new HashSet<>()).add(i);
        }
        for (FaunaTokenType type : mapIndexes.keySet()) {
            if (mapIndexes.get(type).size() >= 3) {
                return mapIndexes.get(type);
            }
        }
        return Collections.emptySet();
    }

    /**
     * Manages the surplus of fauna tokens in the lots by discarding them if necessary.
     *
     * @param pioche the draw pile where the tokens will be recycled.
     * @throws NullPointerException if the pioche is null.
     */
    public void manageSurpop(Pioche pioche) {
        Objects.requireNonNull(pioche);
        Set<Integer> indexes = surpopIndexes();
        if (!indexes.isEmpty()) {
            while (indexes.size() == 4) {
                discardMultipleTokens((List<Integer>) indexes, pioche);
                indexes = surpopIndexes();
            }
            
        }
    }

    /**
     * Asks the user if they want to discard multiple fauna tokens.
     *
     * @param indexes the list of indices corresponding to the lots whose tokens will be discarded.
     * @param pioche the draw pile where the tokens will be recycled.
     */
    private void dismissTokenAsk(List<Integer> indexes, Pioche pioche) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ecarter les 3 tokens ? Oui:1 Non:0");
        switch (scanner.nextInt()) {
            case 1:
                discardMultipleTokens(indexes, pioche);
                break;
            default:
                System.out.println("Aucun token écarté");
        }
        scanner.close();
    }

    /**
     * Fills any empty lots by drawing new habitats and fauna tokens from the Pioche.
     *
     * @param pioche the draw pile from which new habitats and fauna tokens will be drawn.
     * @throws NullPointerException if the pioche is null.
     */
    public void fillEmptyLots(Pioche pioche) {
        Objects.requireNonNull(pioche);
        for (int i = 0; i < lots.size(); i++) {
            if (lots.get(i).isEmpty()) {
                lots.set(i, new Lot(Optional.of(pioche.piocherHabitat()), Optional.of(pioche.piocherToken())));
            } else {
                if (lots.get(i).tile().isEmpty()) {
                    lots.set(i, new Lot(Optional.of(pioche.piocherHabitat()), lots.get(i).typeToken()));
                }
                if (lots.get(i).typeToken().isEmpty()) {
                    lots.set(i, new Lot(lots.get(i).tile(), Optional.of(pioche.piocherToken())));
                }
            }
        }
    }
}
