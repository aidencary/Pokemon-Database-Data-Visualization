package Filter;

import Data.Pokemon;

import java.util.List;

public interface FilterObserver {
    void onFilterUpdate(List<Pokemon> filteredList);
}
