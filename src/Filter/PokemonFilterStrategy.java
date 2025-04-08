package Filter;

import Data.Pokemon;

// Interface for implementing Strategy design pattern for filtering
public interface PokemonFilterStrategy {
    boolean filter(Pokemon p);
}
