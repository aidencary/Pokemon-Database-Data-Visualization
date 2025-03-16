package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PokemonGUI {
    public PokemonGUI(List<Pokemon> pokeList) {
        final int frameWidth = 1284;
        final int frameHeight = 800;

        JFrame frame = new JFrame("PokéApp");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        frame.setLayout(new BorderLayout());

        TablePanel tablePanel = new TablePanel(pokeList);
        DetailsPanel detailsPanel = new DetailsPanel();
        StatsPanel  statsPanel = new StatsPanel(pokeList);
        TypeChartPanel chartPanel = new TypeChartPanel(pokeList);


        tablePanel.setDetailsPanel(detailsPanel);

        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(detailsPanel, BorderLayout.SOUTH);
        frame.add(statsPanel, BorderLayout.EAST);
        frame.add(chartPanel, BorderLayout.WEST);

        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
    }
 }

