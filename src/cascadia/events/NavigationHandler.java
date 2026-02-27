package cascadia.events;

import com.github.forax.zen.KeyboardEvent;

import cascadia.utils.MutableInt;

/**
 * Handles navigation on the map using arrow keys.
 */
public final class NavigationHandler implements Handler {
    private final MutableInt translateX;
    private final MutableInt translateY;
    private final int stepSize;

    /**
     * Constructs a NavigationHandler.
     *
     * @param initialTranslateX the initial horizontal translation.
     * @param initialTranslateY the initial vertical translation.
     * @param stepSize          the step size for each key press.
     */
    public NavigationHandler(int initialTranslateX, int initialTranslateY, int stepSize) {
        this.translateX = new MutableInt(initialTranslateX);
        this.translateY = new MutableInt(initialTranslateY);
        this.stepSize = stepSize;
    }

    /**
     * Processes a KeyboardEvent to update the translation values.
     *
     * @param keyboardEvent the KeyboardEvent to process.
     */
    @Override
    public void handleEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.action() == KeyboardEvent.Action.KEY_PRESSED) {
            switch (keyboardEvent.key()) {
                case RIGHT -> translateX.decrement(stepSize);
                case LEFT -> translateX.increment(stepSize);
                case DOWN -> translateY.decrement(stepSize);
                case UP -> translateY.increment(stepSize);
            }
        }
    }

    /**
     * Returns the current horizontal translation.
     *
     * @return the horizontal translation.
     */
    public int getTranslateX() {
        return translateX.getValue();
    }

    /**
     * Returns the current vertical translation.
     *
     * @return the vertical translation.
     */
    public int getTranslateY() {
        return translateY.getValue();
    }

		@Override
		public void resetHandler() {
			// TODO Auto-generated method stub
			
		}
}
