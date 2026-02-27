package cascadia.game;



import java.util.Objects;

import cascadia.board.Board;
import cascadia.board.BoardManager;
import cascadia.events.EventState;


/**
 * Represents the game loop that manages the flow of the game, including player actions and game state updates.
 */
public class GameLoop {


    private final BoardManager boardManager;
   

    /**
     * Constructs a new GameLoop with the specified player names and game variant.
     *
     * @param firstPlayerName the name of the first player
     * @param secondPlayerName the name of the second player
     * @param isFamilyVariant true if the family variant is chosen, false otherwise
     * @throws NullPointerException if either player name is null
     */
    public GameLoop(String firstPlayerName, String secondPlayerName, boolean isFamilyVariant) {
        Objects.requireNonNull(firstPlayerName);
        Objects.requireNonNull(secondPlayerName);

        
        this.boardManager = new BoardManager(firstPlayerName, secondPlayerName, isFamilyVariant);
    }

    /**
     * Initializes the game by creating a new GameLoop instance and setting up the game board.
     *
     * @return the initialized GameLoop instance
     */
    public static GameLoop initGame() {
        GameLoop gameLoop = new GameLoop(GamePlay.askName(), GamePlay.askName(), GamePlay.askVariant());
        gameLoop.boardManager.initBoardManager();
        return gameLoop;
    }


    








    /**
     * Executes one loop of the game for a player.
     *
     * @param i the index of the player
     */
    private void oneLoop(int i) {
        System.out.println("DEBUT DU TOUR, IL RESTE " + boardManager.getGameBoard().getStack().getPiocheTuiles().size() + " TUILES");
        boardManager.getGameBoard().getOpLots().manageSurpop(boardManager.getGameBoard().getStack());
        System.out.println(boardManager.getGameBoard().getOpLots().toString());
        System.out.println(boardManager.getGameBoard().getPlayers().get(i).toString());
        boardManager.chooseLot(boardManager.getGameBoard().getPlayers().get(i), i);
        System.out.println(boardManager.getGameBoard().getPlayers().get(i).toString());
        boardManager.placeTile(boardManager.getGameBoard().getPlayers().get(i), i);
        System.out.println(boardManager.getGameBoard().getPlayers().get(i).getPlayerMap().toString());
        if (!boardManager.placeToken(boardManager.getGameBoard().getPlayers().get(i), i)) {
        	boardManager.getGameBoard().getStack().recyclerToken(boardManager.getGameBoard().getPlayers().get(i).getPlayerLot().get().typeToken().get());
        }
        boardManager.getGameBoard().getOpLots().fillEmptyLots(boardManager.getGameBoard().getStack());
        System.out.println(boardManager.getGameBoard().getPlayers().get(i).toString());
        System.out.println("----------------------------------------FIN DU TOUR----------------------------------------------");
    }

    /**
     * Calculate the final score of all players.
     */
    public void calculateFinalScores() {
      for (Player player : boardManager.getGameBoard().getPlayers()) {
          player.getPlayerMap().getScore(); // Calcul du score basé sur l'état final des groupes
          System.out.println("Score final pour " + player.getPlayerName() + ": " + player.getPlayerMap().getScore());
      }
    }

    /**
     * Runs the game loop until the game ends.
     */
    public void runGame() {
    	boardManager.getGameBoard().initBoard();
    	while (boardManager.partyEnder()) {
        for (int i = 0; i < 2; i++) {
            if (boardManager.partyEnder()) {
                oneLoop(i);
            } else {
                break;
            }
        }
    	}
    	calculateFinalScores();
    }
}

