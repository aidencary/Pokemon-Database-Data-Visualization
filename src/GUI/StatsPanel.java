package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generates statistical information about the Pokémon list, including:
 * - Total Pokémon count
 * - Number and percentage of Legendary Pokémon
 * - Type distribution percentages (sorted descending)
 * - Lowest, average, and highest total stats
 *
 * @param pokemonList The list of Pokémon to analyze.
 * @return A formatted string containing the statistics.
 */
public class StatsPanel extends JPanel {
    // String constants
    public static final String HEADER_FORMAT = "Total Pokémon: %d\nLegendary Pokémon: %d (%.2f%%)\n\nType Distribution:\n%s\n\n";
    public static final String STAT_LINE_FORMAT = "%s - Min: %d, Avg: %.2f, Max: %d\n";
    public static final String STAT_SUMMARY_HEADER = "Min, Avg, Max Stats Summary:\n";

    // Integer constants
    public static final int NUM_OF_ROWS = 10;
    public static final int NUM_OF_COLUMNS = 35;

    public StatsPanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        JTextArea statsArea = new JTextArea(NUM_OF_ROWS, NUM_OF_COLUMNS);
        statsArea.setEditable(false);
        add(new JScrollPane(statsArea), BorderLayout.CENTER);
        String statsText = generateStats(pokemonList);
        statsArea.setText(statsText);
    }

    // Generates stats and makes a String containing the stats
    private String generateStats(List<Pokemon> pokemonList) {
        long totalPokemon = pokemonList.size();

        // Stream to count the number of Pokemon
        long legendaryCount = pokemonList.stream()
                .filter(Pokemon::legendary)
                .count();
        double legendaryPercentage = (legendaryCount * 100) / totalPokemon;

        // Counts the occurrences of each Pokemon type (from type1 and type2 columns)
        Map<String, Long> typeCounts = pokemonList.stream()
                .flatMap(p -> Stream.of(p.type1(), p.type2())) // Extracts both type1 and type2
                .filter(type -> !type.equals("None")) // Excludes Pokemon with no secondary type
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        // Formats type distribution percentages, sorting by highest count first
        String typeStats = typeCounts.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue())) // Sort by count descending
                .map(entry -> String.format("%s: %.2f%%", entry.getKey(), (entry.getValue() * 100.0) / totalPokemon))
                .collect(Collectors.joining("\n")); // Join into a multiline String

        // Percentage of Pokémon with Dual Types
        long dualTypeCount = pokemonList.stream().filter(p -> !p.type2().equals("None")).count(); // Count Pokémon with Type 2
        double dualTypePercentage = (dualTypeCount * 100.0) / totalPokemon; // Calculate percentage
        double singleTypePercentage = 100.0 - dualTypePercentage; // Calculate percentage for single types

        // Count of Pokémon Per Generation
        Map<String, Long> generationCounts = pokemonList.stream()
                .collect(Collectors.groupingBy(Pokemon::generation, Collectors.counting())); // Count Pokémon by Generation

        // Formats generation stats
        String generationStats = generationCounts.entrySet().stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey())) // Sort generations numerically
                .map(entry -> String.format("Generation %s: %d Pokémon", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n")); // Join into a multiline String

        // Legendary Pokémon Breakdown by Generation
        Map<String, Long> legendaryPerGeneration = pokemonList.stream()
                .filter(Pokemon::legendary) // Filter only legendary Pokémon
                .collect(Collectors.groupingBy(Pokemon::generation, Collectors.counting())); // Count by Generation

        // Formats legendary Pokémon stats
        String legendaryStats = legendaryPerGeneration.entrySet().stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey())) // Sort generations numerically
                .map(entry -> String.format("Generation %s: %d Legendary Pokémon", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n")); // Join into a multiline String

        // Generate formatted header
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append(String.format(HEADER_FORMAT, totalPokemon, legendaryCount, legendaryPercentage, typeStats));
        statsBuilder.append(STAT_SUMMARY_HEADER);

        // Append stats for each category using constants
        statsBuilder.append(formatStat("HP", pokemonList, Pokemon::hp));
        statsBuilder.append(formatStat("Attack", pokemonList, Pokemon::attack));
        statsBuilder.append(formatStat("Defense", pokemonList, Pokemon::defense));
        statsBuilder.append(formatStat("Sp. Atk", pokemonList, Pokemon::spAtk));
        statsBuilder.append(formatStat("Sp. Def", pokemonList, Pokemon::spDef));
        statsBuilder.append(formatStat("Speed", pokemonList, Pokemon::speed));
        statsBuilder.append(formatStat("Total", pokemonList, Pokemon::total));

        // Append new stats
        statsBuilder.append("\nSingle-Type Pokémon: ").append(String.format("%.2f%%", singleTypePercentage));
        statsBuilder.append("\nDual-Type Pokémon: ").append(String.format("%.2f%%", dualTypePercentage));

        statsBuilder.append("\n\nPokémon Count by Generation:\n").append(generationStats);
        statsBuilder.append("\n\nLegendary Pokémon by Generation:\n").append(legendaryStats);

        return statsBuilder.toString();
    }

    // Formats the min, avg, and max values of a stat
    private String formatStat(String statName, List<Pokemon> pokemonList, ToIntFunction<Pokemon> statFunction) {
        int minStat = pokemonList.stream().mapToInt(statFunction).min().orElse(0);
        int maxStat = pokemonList.stream().mapToInt(statFunction).max().orElse(0);
        double avgStat = pokemonList.stream().mapToInt(statFunction).average().orElse(0);

        return String.format(STAT_LINE_FORMAT, statName, minStat, avgStat, maxStat);
    }
}
