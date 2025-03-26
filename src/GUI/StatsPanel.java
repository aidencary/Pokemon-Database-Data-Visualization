package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Displays statistics for the filtered list of Pokémon using a custom font.
 */
public class StatsPanel extends JPanel {
    // String constants
    public static final String HEADER_FORMAT = "Total Pokémon: %d\nLegendary Pokémon: %d (%.2f%%)\n\nType Distribution:\n%s\n\n";
    public static final String STAT_LINE_FORMAT = "%s - Min: %d, Avg: %.2f, Max: %d\n";
    public static final String STAT_SUMMARY_HEADER = "Min, Avg, Max Stats Summary:\n";

    // Integer constants
    public static final int NUM_OF_ROWS = 10;
    public static final int NUM_OF_COLUMNS = 35;

    private final JTextArea statsArea;

    public StatsPanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());

        // Create and format the stats display area
        statsArea = new JTextArea(NUM_OF_ROWS, NUM_OF_COLUMNS);
        statsArea.setEditable(false);
        statsArea.setFont(loadCustomFont()); // Set custom font
        statsArea.setForeground(Color.BLACK);

        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        updateStats(pokemonList); // Display initial stats
    }

    // Attempts to load the custom Pokémon font from src/Data
    private Font loadCustomFont() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("src/Data/Pokemon Hollow.ttf"));
            return font.deriveFont(Font.PLAIN, 15f); // Return the font with adjusted size
        } catch (FontFormatException | IOException e) {
            System.err.println("Could not load custom font. Falling back to default.");
            return new Font("SansSerif", Font.BOLD, 16);
        }
    }

    // Updates the stats panel with new filtered list
    public void updateStats(List<Pokemon> filteredList) {
        statsArea.setText(generateStats(filteredList));
    }

    // Builds the entire statistics string
    private String generateStats(List<Pokemon> pokemonList) {
        long totalPokemon = pokemonList.size();
        if (totalPokemon == 0) return "No data to display.";

        // Count legendary Pokémon and calculate percentage
        long legendaryCount = pokemonList.stream().filter(Pokemon::legendary).count();
        double legendaryPercentage = (legendaryCount * 100.0) / totalPokemon;

        // Count each type from type1 and type2, excluding "None"
        Map<String, Long> typeCounts = pokemonList.stream()
                .flatMap(p -> Stream.of(p.type1(), p.type2()))
                .filter(type -> !type.equals("None"))
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        // Build the type distribution breakdown (sorted by frequency)
        String typeStats = typeCounts.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(entry -> String.format("%s: %.2f%%", entry.getKey(), (entry.getValue() * 100.0) / totalPokemon))
                .collect(Collectors.joining("\n"));

        // Count Pokémon with and without secondary types
        long dualTypeCount = pokemonList.stream().filter(p -> !p.type2().equals("None")).count();
        double dualTypePercentage = (dualTypeCount * 100.0) / totalPokemon;
        double singleTypePercentage = 100.0 - dualTypePercentage;

        // Append everything to the output
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append(String.format(HEADER_FORMAT, totalPokemon, legendaryCount, legendaryPercentage, typeStats));
        statsBuilder.append(STAT_SUMMARY_HEADER);

        // Append min/avg/max for each stat
        statsBuilder.append(formatStat("HP", pokemonList, Pokemon::hp));
        statsBuilder.append(formatStat("Attack", pokemonList, Pokemon::attack));
        statsBuilder.append(formatStat("Defense", pokemonList, Pokemon::defense));
        statsBuilder.append(formatStat("Sp. Atk", pokemonList, Pokemon::spAtk));
        statsBuilder.append(formatStat("Sp. Def", pokemonList, Pokemon::spDef));
        statsBuilder.append(formatStat("Speed", pokemonList, Pokemon::speed));
        statsBuilder.append(formatStat("Total", pokemonList, Pokemon::total));

        // Append dual/single type percentage breakdown
        statsBuilder.append("\nSingle-Type Pokémon: ").append(String.format("%.2f%%", singleTypePercentage));
        statsBuilder.append("\nDual-Type Pokémon: ").append(String.format("%.2f%%", dualTypePercentage));

        // Replace % with " percent" if the custom font doesn't support it
        return statsBuilder.toString().replace("%", " percent");
    }

    // Calculates the min, avg, and max values for a given stat
    private String formatStat(String statName, List<Pokemon> pokemonList, ToIntFunction<Pokemon> statFunction) {
        int minStat = pokemonList.stream().mapToInt(statFunction).min().orElse(0);
        int maxStat = pokemonList.stream().mapToInt(statFunction).max().orElse(0);
        double avgStat = pokemonList.stream().mapToInt(statFunction).average().orElse(0);
        return String.format(STAT_LINE_FORMAT, statName, minStat, avgStat, maxStat);
    }
}
