package Filter;


import Data.Pokemon;

// Filter for legendary Pokemon
public class LegendaryFilter implements PokemonFilterStrategy {
    @Override
    public boolean filter(Pokemon p) {
        return p.legendary();
    }
}
