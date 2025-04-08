package Filter;

import Data.Pokemon;

import java.util.List;

// Observer used to notify the panels when to update after a filter is applied
public interface FilterObserver {
    void onFilterUpdate(List<Pokemon> filteredList);
}
