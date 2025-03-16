package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PokemonGUI {
    public PokemonGUI(List<Pokemon> pokeList) {
        final int frameWidth = 695;
        final int frameHeight = 800;

        JFrame frame = new JFrame("PokéApp");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        frame.setLayout(new BorderLayout());

        TablePanel tablePanel = new TablePanel(pokeList);
        DetailsPanel detailsPanel = new DetailsPanel();

        tablePanel.setDetailsPanel(detailsPanel);

        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(detailsPanel, BorderLayout.SOUTH);

        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
    }
 }

