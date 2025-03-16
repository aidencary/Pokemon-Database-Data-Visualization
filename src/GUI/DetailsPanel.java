package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private JTextArea detailsArea;

    public DetailsPanel() {
        setLayout(new BorderLayout());
        detailsArea = new JTextArea(4, 50);
        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }

    public void updateDetails(Pokemon p) {
        detailsArea.setText(String.format("Pokédex Number: %s\nBase Stat Total: %d\nGeneration: %s\nLegendary: %b",
                p.id(), p.total(), p.generation(), p.legendary()));;
    }

}
