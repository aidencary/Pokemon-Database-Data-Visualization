package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PokeApp {
    // Integer constants
    public static final int FRAME_WIDTH = 1552;
    public static final int FRAME_HEIGHT = 860;
    public static final String filePath = "src/Data/updatedPokemon.csv";

    public static void main(String[] args) {
        // Creates a List of Pokemon
        List<Pokemon> pokeList =  loadPokemon(filePath);
        if (pokeList.isEmpty()) {
            System.out.println("No Pokémon Found");
            return;
        }

        // Console Output
        consoleOutput(pokeList);

        // GUI
        SwingUtilities.invokeLater(() -> startGUI(pokeList));

    }

    // Prints test console output to the terminal
    private static void consoleOutput(List<Pokemon> pokeList) {
        System.out.println("1st Pokémon: " + pokeList.get(0));
        System.out.println("10th Pokémon: " + pokeList.get(9));
        System.out.println("Total Pokémon Including Megas and Alt Forms (From Gen 1 to Gen 6): " + pokeList.size() );
    }

    // Reads data file the file using a stream and returns a list of Pokemon
    private static List<Pokemon> loadPokemon(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .skip(1) // Skip header
                    .map(line -> line.split(",")) // Split each line into an array of Strings using commas
                    .map(Pokemon::new) // Convert each String array into a Pokemon object
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Used to set window and all panels along with their observers
    private static void startGUI(List<Pokemon> pokeList) {
        // Create frame and icon
        JFrame frame = new JFrame("PokéApp");
        ImageIcon icon = new ImageIcon("src/GUI/pokeIcon.png");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.setLayout(new BorderLayout());

        // Panels
        TablePanel tablePanel = new TablePanel(pokeList);
        StatsPanel statsPanel = new StatsPanel(pokeList);
        DetailsPanel detailsPanel = new DetailsPanel();
        TypeChartPanel chartPanel = new TypeChartPanel(pokeList);

        // Observer registration
        tablePanel.addFilterObserver(detailsPanel);
        tablePanel.addFilterObserver(statsPanel);
        tablePanel.addFilterObserver(chartPanel);

        // Link panels
        tablePanel.setStatsPanel(statsPanel);
        tablePanel.setDetailsPanel(detailsPanel);
        tablePanel.setTypeChartPanel(chartPanel);

        // Add to frame
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(detailsPanel, BorderLayout.SOUTH);
        frame.add(statsPanel, BorderLayout.EAST);
        frame.add(chartPanel, BorderLayout.WEST);

        // Size frame size
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }

}
