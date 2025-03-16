package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PokemonGUI {
    // Integer constants
    final int FRAME_WIDTH = 1482;
    final int FRAME_HEIGHT = 910;

    public PokemonGUI(List<Pokemon> pokeList) {

        JFrame frame = new JFrame("PokéApp");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        frame.setLayout(new BorderLayout());

        // Create all panels
        TablePanel tablePanel = new TablePanel(pokeList);
        DetailsPanel detailsPanel = new DetailsPanel();
        StatsPanel  statsPanel = new StatsPanel(pokeList);
        TypeChartPanel chartPanel = new TypeChartPanel(pokeList);

        // Set the DetailsPanel
        tablePanel.setDetailsPanel(detailsPanel);

        // Add frames for all panels
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(detailsPanel, BorderLayout.SOUTH);
        frame.add(statsPanel, BorderLayout.EAST);
        frame.add(chartPanel, BorderLayout.WEST);

        // Size frame size
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }

 }

