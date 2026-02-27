package cascadia.events;

import java.util.Objects;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;

/**
 * Interface who to Manage Events
 * 
 */
public sealed interface Handler permits NavigationHandler,OptionsSelectionHandler, ItemPlacementHandler {
	
	void handleEvent(KeyboardEvent keyboardEvent);
	void resetHandler();
	
	public static void handleEvents(ApplicationContext context, NavigationHandler navigationHandler, OptionsSelectionHandler selectionHandler, ItemPlacementHandler tilePlacementHandler) {
    Objects.requireNonNull(navigationHandler);
    Objects.requireNonNull(selectionHandler);
    Objects.requireNonNull(tilePlacementHandler);
		var event = context.pollEvent();
    if (event instanceof KeyboardEvent keyboardEvent) {
        navigationHandler.handleEvent(keyboardEvent);
        selectionHandler.handleEvent(keyboardEvent);
        tilePlacementHandler.handleEvent(keyboardEvent);
    }
}

}
