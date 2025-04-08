package GUI;

import Data.Pokemon;
import Filter.FilterObserver;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class DetailsPanel extends JPanel implements FilterObserver {
    // JTextPane to display details about the selected Pokémon
    private JTextPane detailsPane;

    public DetailsPanel() {
        setLayout(new BorderLayout()); // Use BorderLayout for placing scrollPane

        // Create text pane and apply styling
        detailsPane = new JTextPane();
        detailsPane.setEditable(false); // Make it non-editable
        detailsPane.setFont(loadCustomFont()); // Set custom font
        detailsPane.setForeground(Color.BLACK); // Set text color

        // Wrap the text pane in a scroll pane for scrollable view
        JScrollPane scrollPane = new JScrollPane(detailsPane);
        scrollPane.setPreferredSize(new Dimension(500, 100)); // Set preferred size

        add(scrollPane, BorderLayout.CENTER); // Add scrollPane to center
    }

    // Shows the hidden details of the selected Pokémon
    public void updateDetails(Pokemon p) {
        // Format text with Pokémon's detailed attributes
        String text = String.format(
                "Pokédex Number: %s\nBase Stat Total: %d\nGeneration: %s\nLegendary: %b",
                p.id(), p.total(), p.generation(), p.legendary());

        detailsPane.setText(text); // Set the text content

        // Center the text horizontally
        StyledDocument doc = detailsPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false); // Apply center alignment
    }

    // Attempts to load the custom Pokémon font from src/Data folder
    private Font loadCustomFont() {
        try {
            // Load the font from the TTF file
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("src/Data/Pokemon Hollow.ttf"));
            return font.deriveFont(Font.PLAIN, 15f); // Adjust font size
        } catch (FontFormatException | IOException e) {
            System.err.println("Could not load custom font. Falling back to default.");
            return new Font("SansSerif", Font.BOLD, 16); // Fallback font
        }
    }

    // Allows the detail panel to react when a filter is applied
    @Override
    public void onFilterUpdate(List<Pokemon> filteredList) {
        if (!filteredList.isEmpty()) {
            updateDetails(filteredList.get(0));
        } else {
            updateDetails(null);
        }
    }

}
