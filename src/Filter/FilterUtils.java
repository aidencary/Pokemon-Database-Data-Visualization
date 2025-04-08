package Filter;

import Data.Pokemon;

import java.util.List;
import java.util.stream.Collectors;

// Filter utilities class used for all filters
public class FilterUtils {
    public static List<Pokemon> applyFilters(List<Pokemon> list, List<PokemonFilterStrategy> strategies) {
        return list.stream()
                .filter(p -> strategies.stream().allMatch(f -> f.filter(p)))
                .collect(Collectors.toList());
    }
}
