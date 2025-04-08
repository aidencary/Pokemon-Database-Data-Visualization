package Filter;

import Data.Pokemon;

public class GenerationFilter implements PokemonFilterStrategy {
    private final String generation;

    // GenerationFilter constructor
    public GenerationFilter(String generation) {
        this.generation = generation;
    }

    // Filter for generations
    @Override
    public boolean filter(Pokemon p) {
        return p.generation().startsWith(generation);
    }
}
