package Filter;

import Data.Pokemon;

public class TypeFilter implements PokemonFilterStrategy{
    private final String type;

    // TypeFilter constructor
    public TypeFilter(String type) {
        this.type = type;
    }

    // Filter for type1 or type2
    @Override
    public boolean filter(Pokemon p) {
        return p.type1().equals(type) || p.type2().equals(type);
    }

}
