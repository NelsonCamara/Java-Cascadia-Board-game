package cascadia.events;



/**
 * Enum representing the states of an event in the Cascadia game.
 * Each state corresponds to a phase in the game's event processing.
 */
public enum EventState {
    INIT,           // Initial state of the game or an event
    CHOOSELOT,      // State where a player chooses a lot
    TILEPLACEMENT,  // State for placing a habitat tile
    TOKENPLACEMENT, // State for placing a fauna token
    END             // End state of the event or game
}
