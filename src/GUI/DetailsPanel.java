package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    // Integer constants
    public static final int NUM_OF_ROWS = 4;
    public static final int NUM_OF_COLUMNS = 50;

    private JTextArea detailsArea;

    public DetailsPanel() {
        setLayout(new BorderLayout());
        detailsArea = new JTextArea(NUM_OF_ROWS, NUM_OF_COLUMNS);
        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }

    // Shows the hidden details of the selected Pokemon
    public void updateDetails(Pokemon p) {
        detailsArea.setText(String.format("Pokédex Number: %s\nBase Stat Total: %d\nGeneration: %s\nLegendary: %b",
                p.id(), p.total(), p.generation(), p.legendary()));;
    }

}
