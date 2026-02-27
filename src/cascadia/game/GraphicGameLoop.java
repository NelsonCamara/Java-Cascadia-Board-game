package cascadia.game;



import cascadia.board.BoardManager;
import cascadia.draw.OptionsLotsDrawer;
import cascadia.draw.PlayerDrawer;
import cascadia.draw.TileDrawer;
import cascadia.events.OptionsSelectionHandler;
import cascadia.items.Tile;
import cascadia.events.EventState;
import cascadia.events.Handler;
import cascadia.events.ItemPlacementHandler;
import cascadia.events.NavigationHandler;
import cascadia.utils.Position;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;

/**
 * Handles the main game loop for the graphical version of Cascadia.
 */
public class GraphicGameLoop {
    private final BoardManager boardManager;       
    private EventState currentState;              
    private int currentPlayerIndex;               

    /**
     * Constructs a GameLoopGraphic.
     *
     * @param firstPlayerName  the name of the first player.
     * @param secondPlayerName the name of the second player.
     * @param isFamilyVariant  whether the family variant is used.
     */
    public GraphicGameLoop(String firstPlayerName, String secondPlayerName, boolean isFamilyVariant) {
    	Objects.requireNonNull(firstPlayerName);
    	Objects.requireNonNull(secondPlayerName);
        this.boardManager = new BoardManager(firstPlayerName, secondPlayerName, isFamilyVariant);
        this.currentState = EventState.INIT;
        this.currentPlayerIndex = 0; // Start with the first player
    }

    /**
     * Initializes the game and sets up the board.
     */
    public void initializeGame() {
        boardManager.initBoardManager();
    }



    /**
     * Retrieves the current player.
     *
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return boardManager.getGameBoard().getPlayers().get(currentPlayerIndex);
    }

    /**
     * Checks if a position is adjacent to any position in the map.
     *
     * @param position the position to check.
     * @param tiles    the map of existing tiles.
     * @return true if the position is adjacent, false otherwise.
     */
    public boolean isPositionAdjacent(Position position, Map<Position, Tile> tiles) {
    		Objects.requireNonNull(position);
        for (Position existingPosition : tiles.keySet()) {
            if (existingPosition.isPosNextTo(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts valid positions (tiles + empty slots) from the PlayerMap.
     *
     * @param tiles the map of existing tiles.
     * @return a set of valid positions.
     */
    private Set<Position> extractValidPositions(Map<Position, Tile> tiles) {
        Set<Position> validPositions = new HashSet<>(tiles.keySet());
        int minX = tiles.keySet().stream().mapToInt(Position::x).min().orElse(0);
        int maxX = tiles.keySet().stream().mapToInt(Position::x).max().orElse(0);
        int minY = tiles.keySet().stream().mapToInt(Position::y).min().orElse(0);
        int maxY = tiles.keySet().stream().mapToInt(Position::y).max().orElse(0);

        minX -= 1;
        maxX += 1;
        minY -= 1;
        maxY += 1;

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Position position = new Position(i, j);
                if (!tiles.containsKey(position)) {
                    validPositions.add(position);
                }
            }
        }

        return validPositions;
    }
    
    
    /**
     * Prepares the screen for selecting options.
     *
     * @param graphics         the graphics context.
     * @param context          the application context.
     * @param optionsHandler   the options selection handler.
     * @param tileHandler      the item placement handler.
     */
    public void prepareOptionSelectionScreen(Graphics2D graphics, ApplicationContext context,
                                             OptionsSelectionHandler optionsHandler,
                                             ItemPlacementHandler tileHandler) {
    		Objects.requireNonNull(optionsHandler);
    		Objects.requireNonNull(tileHandler);
        setCurrentState(EventState.CHOOSELOT);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());

        PlayerDrawer.drawFixedPlayerElements(graphics, getCurrentPlayer(),
                context.getScreenInfo().width(), context.getScreenInfo().height());

        OptionsLotsDrawer.drawOptionsLotsCaller(graphics, boardManager.getGameBoard().getOpLots(), optionsHandler);

        tileHandler.resetHandler();
    }
    
    
    /**
     * Handles the tile placement logic during the TILEPLACEMENT state.
     *
     * @param gameLoop the game loop instance.
     * @param graphics the graphics context.
     * @param tileHandler the tile placement handler.
     * @param navigationHandler the navigation handler.
     */
    public static void handleTilePlacement(GraphicGameLoop gameLoop, Graphics2D graphics,
                                            ItemPlacementHandler tileHandler, NavigationHandler navigationHandler) {
    	
    	Objects.requireNonNull(tileHandler);
    	Objects.requireNonNull(navigationHandler);
        TileDrawer.drawSelector(graphics, tileHandler.getCurrentPixelPosition(),
                navigationHandler.getTranslateX(), navigationHandler.getTranslateY());
        if (tileHandler.isPosChosen()) {
            Position chosenPos = tileHandler.getChosenPos();
            if (Position.isPositionAdjacent(chosenPos, gameLoop.getCurrentPlayer().getPlayerMap().getTuiles())) {
                gameLoop.getBoardManager().graphicTilePlacement(gameLoop.getCurrentPlayer(),
                        gameLoop.getCurrentPlayerIndex(), chosenPos);
                gameLoop.setCurrentState(EventState.TOKENPLACEMENT);
                tileHandler.resetHandler();
            }
        }
    }
    
    
    
    /**
     * Handles the token placement logic during the TOKENPLACEMENT state.
     *
     * @param gameLoop the game loop instance.
     * @param graphics the graphics context.
     * @param tileHandler the tile placement handler.
     * @param navigationHandler the navigation handler.
     */
    public static void handleTokenPlacement(GraphicGameLoop gameLoop, Graphics2D graphics,
                                             ItemPlacementHandler tileHandler, NavigationHandler navigationHandler) {
    	Objects.requireNonNull(tileHandler);
    	Objects.requireNonNull(navigationHandler);  
    	TileDrawer.drawSelector(
            graphics,
            tileHandler.getCurrentPixelPosition(),
            navigationHandler.getTranslateX(),
            navigationHandler.getTranslateY()
        );
        if (tileHandler.isPosChosen()) {
            Position chosenPos = tileHandler.getChosenPos();
            if (gameLoop.getBoardManager().graphicTokenPlacement(gameLoop.getCurrentPlayer(), gameLoop.getCurrentPlayerIndex(), chosenPos)) {
                gameLoop.setCurrentState(EventState.END);
                tileHandler.resetHandler();
            } else {
                System.out.println("Invalid position for token placement.");
                gameLoop.getBoardManager().getGameBoard().getStack().recyclerToken(
                    gameLoop.getBoardManager().getGameBoard().getPlayers()
                        .get(gameLoop.getCurrentPlayerIndex())
                        .getPlayerLot()
                        .get()
                        .typeToken()
                        .get()
                );
                gameLoop.setCurrentState(EventState.END);
                tileHandler.resetHandler();
            }
        }
    }
    
    
    
    /**
     * Renders the main game frame.
     *
     * @param graphics the graphics context.
     * @param context the application context.
     * @param gameLoop the game loop instance.
     * @param navigationHandler the navigation handler.
     */
    public static void renderGameFrame(Graphics2D graphics, ApplicationContext context, 
                                        GraphicGameLoop gameLoop, NavigationHandler navigationHandler) {
    	Objects.requireNonNull(gameLoop);
    	Objects.requireNonNull(navigationHandler);
    	  graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
        gameLoop.getBoardManager().getGameBoard().getOpLots().manageSurpop(gameLoop.getBoardManager().getGameBoard().getStack());
        PlayerDrawer.drawPlayerMap(graphics, gameLoop.getCurrentPlayer().getPlayerMap().getTuiles(), 
                                   navigationHandler.getTranslateX(), navigationHandler.getTranslateY());
        PlayerDrawer.drawFixedPlayerElements(graphics, gameLoop.getCurrentPlayer(), 
                                             context.getScreenInfo().width(), context.getScreenInfo().height());
    }
    
    /**
     * Handles the logic during the END state.
     *
     * @param gameLoop the game loop instance.
     * @param tileHandler the tile placement handler.
     * @param optionsHandler the options selection handler.
     */
    public static void handleEndState(GraphicGameLoop gameLoop, ItemPlacementHandler tileHandler,
                                       OptionsSelectionHandler optionsHandler) {
    		Objects.requireNonNull(tileHandler);
    		Objects.requireNonNull(optionsHandler);
    		gameLoop.setCurrentPlayerIndex((gameLoop.getCurrentPlayerIndex() + 1) % gameLoop.getBoardManager().getGameBoard().getPlayers().size());
        gameLoop.getBoardManager().getGameBoard().getOpLots().fillEmptyLots(gameLoop.getBoardManager().getGameBoard().getStack());
        tileHandler.resetHandler();
        optionsHandler.resetHandler();
        gameLoop.setCurrentState(EventState.CHOOSELOT);
    }
    
    
    /**
     * Runs the game with the specified player names and variant.
     *
     * @param firstPlayerName the name of the first player.
     * @param secondPlayerName the name of the second player.
     * @param isFamilyVariant boolean indicating if the family variant is used.
     */
    public static void runGame(String firstPlayerName, String secondPlayerName, boolean isFamilyVariant) {
        Application.run(Color.WHITE, context -> {
            GraphicGameLoop gameLoop = new GraphicGameLoop(firstPlayerName, secondPlayerName, isFamilyVariant);
            gameLoop.initializeGame();
            NavigationHandler navigationHandler = new NavigationHandler(0, 0, 20);
            OptionsSelectionHandler optionsHandler = new OptionsSelectionHandler(gameLoop.getBoardManager().getGameBoard().getOpLots().getLots().size());
            ItemPlacementHandler tileHandler = new ItemPlacementHandler(Position.extractValidPositions(gameLoop.getCurrentPlayer().getPlayerMap().getTuiles()), 110);

            while (gameLoop.getBoardManager().partyEnder()) {
                Handler.handleEvents(context, navigationHandler, optionsHandler, tileHandler);
                context.renderFrame(graphics -> {
                    GraphicGameLoop.renderGameFrame(graphics, context, gameLoop, navigationHandler);

                    if (optionsHandler.isVisible()) {
                        gameLoop.prepareOptionSelectionScreen(graphics, context, optionsHandler, tileHandler);
                    }

                    if (optionsHandler.getChosenIndex() != -1 && gameLoop.getCurrentState() == EventState.CHOOSELOT) {
                        gameLoop.getBoardManager().graphicChooseLot(gameLoop.getCurrentPlayer(), gameLoop.getCurrentPlayerIndex(), optionsHandler.getChosenIndex());
                        gameLoop.setCurrentState(EventState.TILEPLACEMENT);
                    }

                    if (gameLoop.getCurrentState() == EventState.TILEPLACEMENT) {
                        GraphicGameLoop.handleTilePlacement(gameLoop, graphics, tileHandler, navigationHandler);
                    }

                    if (gameLoop.getCurrentState() == EventState.TOKENPLACEMENT) {
                        GraphicGameLoop.handleTokenPlacement(gameLoop, graphics, tileHandler, navigationHandler);
                    }

                    if (gameLoop.getCurrentState() == EventState.END) {
                        GraphicGameLoop.handleEndState(gameLoop, tileHandler, optionsHandler);
                    }
                });
            }
            System.out.println(gameLoop.getBoardManager().getGameBoard().finalScores());
        });
    }
    
		public EventState getCurrentState() {
			// TODO Auto-generated method stub
			return  currentState;
		}
		
		public void setCurrentState(EventState newState) {
			currentState = newState;
		}

		public BoardManager getBoardManager() {
			// TODO Auto-generated method stub
			return boardManager;
		}

		

		public int getCurrentPlayerIndex() {
			// TODO Auto-generated method stub
			return currentPlayerIndex;
		}
		
		public void setCurrentPlayerIndex(int index) {
			currentPlayerIndex = index;
		}
}
