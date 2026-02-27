package cascadia.events;
import java.util.List;

import com.github.forax.zen.KeyboardEvent;


/**
 * Handles navigation and selection in OptionsLots using arrow keys and SPACE.
 */
public final class OptionsSelectionHandler implements Handler {
    private int selectedIndex;
    private int chosenIndex;
    private final int optionsSize;
    private boolean isVisible; 

    /**
     * Constructs an OptionsSelectionHandler.
     *
     * @param optionsSize the number of options in OptionsLots.
     */
    public OptionsSelectionHandler(int optionsSize) {
        if (optionsSize <= 0) {
            throw new IllegalArgumentException("Options size must be greater than zero.");
        }
        this.optionsSize = optionsSize;
        this.selectedIndex = 0;
        this.chosenIndex = -1;
        this.isVisible = false;
    }

    /**
     * Processes a KeyboardEvent to update the selected or chosen index, and toggle visibility.
     *
     * @param keyboardEvent the KeyboardEvent to process.
     */
    @Override
    public void handleEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.action() == KeyboardEvent.Action.KEY_PRESSED) {
            switch (keyboardEvent.key()) {
                case LEFT -> moveLeft();
                case RIGHT -> moveRight();
                case O -> toggleVisibility(); 
                case L -> {
                    if (isVisible) {
                        chooseCurrent();
                        toggleVisibility(); 
                    }
                }
            }
        }
    }

    /**
     * Toggles the visibility of OptionsLots.
     */
    public void toggleVisibility() {
        isVisible = !isVisible;
    }

    /**
     * Returns the visibility state of OptionsLots.
     *
     * @return true if OptionsLots is visible, false otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Returns the current selected index.
     *
     * @return the selected index.
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Returns the chosen index.
     *
     * @return the chosen index.
     */
    public int getChosenIndex() {
        return chosenIndex;
    }

    private void moveLeft() {
        selectedIndex = (selectedIndex - 1 + optionsSize) % optionsSize;
    }

    private void moveRight() {
        selectedIndex = (selectedIndex + 1) % optionsSize;
    }

    private void chooseCurrent() {
        chosenIndex = selectedIndex;
        System.out.println("Chosen Lot Index: " + chosenIndex);
    }

		@Override
		public void resetHandler() {
			selectedIndex=0;
			chosenIndex = -1;
			isVisible = false;
		}
}

