package cascadia.events;

import com.github.forax.zen.KeyboardEvent;

import java.util.Set;

import cascadia.utils.Position;



import com.github.forax.zen.KeyboardEvent;

import java.util.Set;

import cascadia.utils.Position;

/**
 * Handles navigation and selection for tile placement using arrow keys and SPACE.
 */
public final class ItemPlacementHandler implements Handler {
    private final Set<Position> validPositions; // Positions (tiles + empty slots) drawn on the map
    private final int spacing; 
    private boolean isPosChosen = false;// Spacing between tiles
    private Position currentPosition;          // Current position of the selector
    private Position chosenPos;
    /**
     * Constructs a TilePlacementHandler.
     *
     * @param validPositions the set of valid positions to navigate and place tiles.
     * @param spacing the spacing between tiles.
     */
    public ItemPlacementHandler(Set<Position> validPositions, int spacing) {
        if (validPositions.isEmpty()) {
            throw new IllegalArgumentException("Valid positions set must not be empty.");
        }
        this.validPositions = validPositions;
        this.spacing = spacing;
        this.currentPosition = validPositions.iterator().next(); // Start at the first valid position
    }

    /**
     * Processes a KeyboardEvent to update the selector's position or confirm the placement.
     *
     * @param keyboardEvent the KeyboardEvent to process.
     */
    @Override
    public void handleEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.action() == KeyboardEvent.Action.KEY_PRESSED) {
            switch (keyboardEvent.key()) {
                case Z -> moveSelector(0, -1);
                case S -> moveSelector(0, 1);
                case Q -> moveSelector(-1, 0);
                case D -> moveSelector(1, 0);
                case SPACE -> confirmPlacement();
            }
        }
    }

    /**
     * Moves the selector to a new position based on arrow key input.
     *
     * @param deltaX the horizontal movement (positive for right, negative for left).
     * @param deltaY the vertical movement (positive for down, negative for up).
     */
    private void moveSelector(int deltaX, int deltaY) {
        Position newPosition = new Position(currentPosition.x() + deltaX, currentPosition.y() + deltaY);

        if (validPositions.contains(newPosition)) {
            currentPosition = newPosition;
            System.out.println("Selector moved to: " + currentPosition);
        } else {
            System.out.println("Cannot move selector to invalid position: " + newPosition);
        }
    }

    /**
     * Confirms the placement of the tile at the current position.
     */
    public void confirmPlacement() {
        if (validPositions.contains(currentPosition)) {
            System.out.println("Tile placed at: " + currentPosition);
            isPosChosen = true;
            setChosenPos(currentPosition);
            
        } else {
            System.out.println("Invalid position for tile placement: " + currentPosition);
            
        }
    }

    /**
     * Converts a Position to a pixel location for rendering.
     *
     * @return the pixel location of the current position.
     */
    public int[] getCurrentPixelPosition() {
        int pixelX = currentPosition.x() * spacing;
        int pixelY = currentPosition.y() * spacing;
        return new int[]{pixelX, pixelY};
    }
    
    @Override
    public void resetHandler() {
    	isPosChosen = false;
    	currentPosition = validPositions.iterator().next();
    	chosenPos = null;
    	
    }

    /**
     * Returns the current position of the selector.
     *
     * @return the current position.
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

		public Position getChosenPos() {
			return chosenPos;
		}

		public void setChosenPos(Position chosenPos) {
			this.chosenPos = chosenPos;
		}

		public boolean isPosChosen() {
			return isPosChosen;
		}


}

