package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StatsPanel extends JPanel {
    public static final String STAT_FORMAT = "Total Pokémon: %d\nLegendary Pokémon: %d (%.2f%%)\n\nType Distribution:\n%s\n\nStats Summary:\nLowest Total Stats: %d\nAverage Total Stats: %.2f\nHighest Total Stats: %d";


    public StatsPanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        JTextArea statsArea = new JTextArea(10, 17);
        statsArea.setEditable(false);
        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        // Compute stats using streams
        String statsText = generateStats(pokemonList);
        statsArea.setText(statsText);
    }

    private String generateStats(List<Pokemon> pokemonList) {
        long totalPokemon = pokemonList.size();
        long legendaryCount = pokemonList.stream().filter(Pokemon::legendary).count();
        double legendaryPercentage = (legendaryCount * 100) / totalPokemon;

        Map<String, Long> typeCounts = pokemonList.stream()
                .flatMap(p -> Stream.of(p.type1(), p.type2()))
                .filter(type -> !type.equals("None"))
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));


        String typeStats = typeCounts.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue())) // Sort by count descending
                .map(entry -> String.format("%s: %.2f%%", entry.getKey(), (entry.getValue() * 100.0) / totalPokemon))
                .collect(Collectors.joining("\n"));

        int minStat = pokemonList.stream().mapToInt(Pokemon::total).min().orElse(0);
        int maxStat = pokemonList.stream().mapToInt(Pokemon::total).max().orElse(0);
        double avgStat = pokemonList.stream().mapToInt(Pokemon::total).average().orElse(0);

        return String.format(STAT_FORMAT,
                totalPokemon, legendaryCount, legendaryPercentage, typeStats, minStat, avgStat, maxStat);

    }
}
