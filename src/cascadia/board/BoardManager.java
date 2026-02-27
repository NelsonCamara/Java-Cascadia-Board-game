package cascadia.board;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import cascadia.game.GamePlay;
import cascadia.game.Player;
import cascadia.items.FaunaToken;
import cascadia.items.FaunaTokenType;
import cascadia.items.Tile;
import cascadia.utils.Position;

/**
 * Manage the Board data manipulation in game.
 */

public class BoardManager {
	private final Board gameBoard;
	
	public BoardManager (String firstPlayerName, String secondPlayerName, boolean isFamilyVariant) {
		Objects.requireNonNull(firstPlayerName);
    Objects.requireNonNull(secondPlayerName);
    this.gameBoard = new Board(firstPlayerName, secondPlayerName, isFamilyVariant);

	}
	
	public Board getGameBoard() {
		return gameBoard;
	}
	
	/**
	 * Initialize the BoardManager field a Board object.
	 */
	public void initBoardManager(){
		gameBoard.initBoard();
	}
	
  /**
   * Checks if the game should end based on the number of remaining tiles.
   *
   * @return true if there are tiles left, false otherwise
   */
  public boolean partyEnder() {
      Stack<Tile> tilesLeft = gameBoard.getStack().getPiocheTuiles();
      return tilesLeft.size() > 0;
  }
  
  
  /**
   * Allows a player to choose a lot from the available options.
   *
   * @param player the player choosing the lot
   * @param playerIndex the index of the player
   */
  public void chooseLot(Player player, int playerIndex) {
  		Objects.requireNonNull(player);
      int indexLot = GamePlay.askChooseLot();
      OptionsLots opLots = gameBoard.getOpLots();
      Player newPlayer = opLots.givePlayerLot(opLots.getLots().get(indexLot), gameBoard.getStack(), player);
      gameBoard.getPlayers().set(playerIndex, newPlayer);
  }
  
  
  /**
   * Allows a player to choose a lot from the available options graphically.
   *
   * @param player the player choosing the lot
   * @param playerIndex the index of the player
   * @param index of the graphically chosen lot
   */
  public void graphicChooseLot(Player player, int playerIndex,int indexLot) {
  	 	Objects.requireNonNull(player);
      OptionsLots opLots = gameBoard.getOpLots();
      Player newPlayer = opLots.givePlayerLot(opLots.getLots().get(indexLot), gameBoard.getStack(), player);
      gameBoard.getPlayers().set(playerIndex, newPlayer);
  }
  
  
  
  /**
   * Updates the player's map by adding a tile at the specified position and updates the player's lot.
   *
   * @param player the player whose map and lot need to be updated
   * @param playerIndex the index of the player in the game's player list
   * @param tile the tile to be added to the player's map
   * @param futureTilePos the position where the tile will be placed
   */
  private void updatePlayerMapAndLot(Player player, int playerIndex, Tile tile, Position futureTilePos) {
      player.getPlayerMap().addTuile(tile, futureTilePos);

      Player updatedPlayer = player.updatedPlayerLot(
          Optional.of(new Lot(Optional.empty(), player.getPlayerLot().get().typeToken()))
      );

      gameBoard.getPlayers().set(playerIndex, updatedPlayer);
  }

  /**
   * Allows a player to place a habitat tile on their map.
   *
   * @param player the player placing the tile
   * @param playerIndex the index of the player
   */
  public void placeTile(Player player, int playerIndex) {
  		Objects.requireNonNull(player);
      if (player.getPlayerLot().isPresent()) {
          System.out.println("Placez votre tuile habitat");
          Tile tile = player.getPlayerLot().get().tile().get();
          Position futureTilePos = checkPos(player);

          updatePlayerMapAndLot(player, playerIndex, tile, futureTilePos);
      }
  }

  /**
   * Allows a player to place a habitat tile on their map.
   *
   * @param player the player placing the tile
   * @param playerIndex the index of the player
   * @param futureTilePos the position graphically chosen for the tile
   */
  public void graphicTilePlacement(Player player, int playerIndex, Position futureTilePos) {
  	  Objects.requireNonNull(player);
      if (player.getPlayerLot().isPresent()) {
          System.out.println("Placez votre tuile habitat");
          Tile tile = player.getPlayerLot().get().tile().get();

          updatePlayerMapAndLot(player, playerIndex, tile, futureTilePos);
      }
  }

  
  /**
   * Checks if a position is valid for placing a habitat tile.
   *
   * @param player the player placing the tile
   * @return the valid position for placing the tile
   */
  private Position checkPos(Player player) {
  		
      Position futureTilePos;
      boolean isPosValid = false;
      futureTilePos = Position.askPosition();
      while (!isPosValid) {
          for (Position pos : player.getPlayerMap().getTuiles().keySet()) {
              if (pos.isPosNextTo(futureTilePos) && !player.getPlayerMap().getTuiles().containsKey(futureTilePos)) {
                  isPosValid = true;
                  break;
              }
          }
          if (isPosValid) {
						break;
					}
          System.out.println("Cette position n'est pas valide pour placer cette tuile " + futureTilePos.toString());
          futureTilePos = Position.askPosition();
      }
      return futureTilePos;
  }
  /**
   * Allows a player to place a fauna token on their map.
   *
   * @param player the player placing the token
   * @param playerIndex the index of the player
   * @return true if the token was successfully placed, false otherwise
   */
  public Boolean placeToken(Player player, int playerIndex) {
  		Objects.requireNonNull(player);
      if (player.getPlayerLot().isPresent()) {
          FaunaTokenType tokenToPut = player.getPlayerLot().get().typeToken().get();
          Set<Position> validPos = player.getPlayerMap().validTokensPositions(tokenToPut);

          if (!validPos.isEmpty()) {
              System.out.println("Placez votre jeton faune");
              Position targetTile = Position.askPosition();
              while (!validPos.contains(targetTile)) {
                  System.out.println("Cette position n'est pas valide pour placer ce jeton faune");
                  targetTile = Position.askPosition();
              }

              updatePlayerMapAndLotWithToken(player, playerIndex, tokenToPut, targetTile);
              return true;
          }

          System.out.println("Vous ne pouvez pas placer ce token");
          return false;
      }
      return false;
  }

  /**
   * Allows a player to place a fauna token on their map graphically.
   *
   * @param player the player placing the token
   * @param playerIndex the index of the player
   * @param chosenPos the position chosen graphically for the token
   * @return true if the token was successfully placed, false otherwise
   */
  public Boolean graphicTokenPlacement(Player player, int playerIndex, Position chosenPos) {
  		Objects.requireNonNull(player);
      if (player.getPlayerLot().isPresent()) {
          FaunaTokenType tokenToPut = player.getPlayerLot().get().typeToken().get();
          Set<Position> validPos = player.getPlayerMap().validTokensPositions(tokenToPut);

          if (!validPos.isEmpty()) {
              System.out.println("Placez votre jeton faune");
              if (!validPos.contains(chosenPos)) {
              	return false;
              }

              updatePlayerMapAndLotWithToken(player, playerIndex, tokenToPut, chosenPos);
              return true;
          }

          System.out.println("Vous ne pourrez pas placer ce token");
          return false;
      }
      return false;
  }
  
  
  /**
   * Updates the player's map by placing a fauna token at the specified position and updates the player's lot.
   *
   * @param player the player whose map and lot need to be updated
   * @param playerIndex the index of the player in the game's player list
   * @param tokenType the type of fauna token to be placed
   * @param targetTile the position where the fauna token will be placed
   */
  private void updatePlayerMapAndLotWithToken(Player player, int playerIndex, FaunaTokenType tokenType, Position targetTile) {
  		player.getPlayerMap().putToken(new FaunaToken(tokenType), targetTile);

      Player updatedPlayer = player.updatedPlayerLot(
          Optional.of(new Lot( Optional.empty(), Optional.empty()))
      );

      gameBoard.getPlayers().set(playerIndex, updatedPlayer);
  }


}
