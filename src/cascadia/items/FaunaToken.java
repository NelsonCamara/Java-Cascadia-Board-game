package cascadia.items;

import java.util.Objects;

/**
 * Represents a fauna token in the Cascadia game. Each fauna token has a specific type.
 * This record ensures that the fauna token type is non-null upon creation.
 *
 * @param tokenType the type of the fauna token (e.g., FOX, BEAR).
 * @throws NullPointerException if the specified token type is null.
 */
public record FaunaToken(FaunaTokenType tokenType) {

    /**
     * Constructor for the JetonFaune record that verifies the token type is non-null.
     */
    public FaunaToken {
        Objects.requireNonNull(tokenType);
    }

    /**
     * Returns the string representation of the fauna token, displaying its type.
     *
     * @return a string representation of the fauna token type.
     */
    @Override
    public String toString() {
        return tokenType.toString();
    }
}