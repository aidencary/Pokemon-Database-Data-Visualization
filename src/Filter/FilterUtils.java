package Filter;

import Data.Pokemon;

import java.util.List;
import java.util.stream.Collectors;

// Filter utilities class used for all filters
public class FilterUtils {
    public static List<Pokemon> applyFilters(List<Pokemon> list, List<PokemonFilterStrategy> strategies) {
        // Returns a list of Pokemon according to what data is being modified and what is being filtered
        return list.stream()
                .filter(p -> strategies.stream().allMatch(f -> f.filter(p)))
                .collect(Collectors.toList());
    }
}
