package GUI;

import Data.Pokemon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TablePanel extends JPanel {
    // String constants
    public static final String SHOW_LEGENDARY = "Show Only Legendary";

    // Constants for column widths
    public static final int NAME_COLUMN_WIDTH = 150;
    public static final int TYPE1_COLUMN_WIDTH = 80;
    public static final int TYPE2_COLUMN_WIDTH = 80;
    public static final int HP_COLUMN_WIDTH = 50;
    public static final int ATTACK_COLUMN_WIDTH = 60;
    public static final int DEFENSE_COLUMN_WIDTH = 60;
    public static final int SPATK_COLUMN_WIDTH = 60;
    public static final int SPDEF_COLUMN_WIDTH = 60;
    public static final int SPEED_COLUMN_WIDTH = 60;

    // Creating tables, panels, comboBoxes, checkBoxes, and pokemon list.
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private JComboBox<String> typeFilter;
    private JComboBox<String> generationFilter;
    private JCheckBox legendaryFilter;
    private List<Pokemon> fullPokemonData; // Stores all Pokémon for filtering
    private DetailsPanel detailsPanel;

    public TablePanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        fullPokemonData = new ArrayList<>(pokemonList); // Store all Pokémon data
        initializeTableModel();
        initializeFilters();
        populateTable(pokemonList);
        initializeTableSorter();
        setupSelectionListener(pokemonList);
        setupColumnWidths();
        addResetButton();
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Initializes all filter comboBoxes and checkBoxes with action listeners and adds them to the panel
    private void initializeFilters() {
        // Create panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        // Create type and generation combo boxes and legendary check box
        typeFilter = new JComboBox<>(new String[]{"All", "Fire", "Water", "Grass", "Electric", "Ice", "Fighting", "Poison", "Ground",
                "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"});
        generationFilter = new JComboBox<>(new String[]{"All", "Generation 1", "Generation 2", "Generation 3", "Generation 4", "Generation 5", "Generation 6"});
        legendaryFilter = new JCheckBox(SHOW_LEGENDARY);

        // Add action listeners for combo boxes and check box
        typeFilter.addActionListener(e -> applyFilters());
        generationFilter.addActionListener(e -> applyFilters());
        legendaryFilter.addActionListener(e -> applyFilters());

        // Add panels
        filterPanel.add(new JLabel("Filter by Type:"));
        filterPanel.add(typeFilter);
        filterPanel.add(new JLabel("Filter by Generation:"));
        filterPanel.add(generationFilter);
        filterPanel.add(legendaryFilter);
        add(filterPanel, BorderLayout.NORTH);
    }

    // Filter implementation
    private void applyFilters() {
        // Put selected type and generation into a string and legendary value into a boolean
        String selectedType = (String) typeFilter.getSelectedItem();
        String selectedGeneration = (String) generationFilter.getSelectedItem();
        boolean showOnlyLegendary = legendaryFilter.isSelected();

        // Extract generation number from selected filter (e.g., "1 (Red Blue and Yellow)" → "1")
        String selectedGenNumber = selectedGeneration.equals("All") ? "All" : selectedGeneration.split(" ")[1];

        // Clear the table model before repopulating
        model.setRowCount(0);

        for (Pokemon p : fullPokemonData) {
            // Extract only the generation number (e.g., "1 (Red Blue and Yellow)" → "1")
            String pokemonGenNumber = p.generation().split(" ")[0];

            // Apply filters
            if (!selectedType.equals("All") && !p.type1().equals(selectedType) && !p.type2().equals(selectedType)) {
                continue;
            }
            if (showOnlyLegendary && !p.legendary()) {
                continue;
            }
            if (!selectedGenNumber.equals("All") && !pokemonGenNumber.equals(selectedGenNumber)) {
            continue;
            }

            // Add filtered data to the table model
            model.addRow(new Object[]{p.name(), p.type1(), p.type2(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()});
        }
    }

    // Initializes the table model
    private void initializeTableModel() {
        // Create columns with their corresponding name
        String[] columns = {"Name", "Type 1", "Type 2", "HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // So cells can not be clicked on and edited
            }
        };

        // Create table
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    // Creates the list of Pokemon and populates the table
    private void populateTable(List<Pokemon> pokemonList) {
        fullPokemonData.clear(); // Clear any existing data
        fullPokemonData.addAll(pokemonList); // Store full data for filtering

        for (Pokemon p : pokemonList) {
            // Add only visible columns to the table model
            model.addRow(new Object[]{p.name(), p.type1(), p.type2(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()});
        }
    }

    // Sorting logic for each column
    private void initializeTableSorter() {
        // Create the table sorter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Highest to Lowest or Lowest to Highest sorting for stats HP, Attack, Defense, spAtk, spDef, and Speed
        for (int i = 3; i <= 8; i++) {
            sorter.setComparator(i, Comparator.comparingInt(o -> (int) o).reversed());
        }

        // Alphabetically sorting for Name, Type 1, and Type 2
        sorter.setComparator(0, Comparator.comparing(o -> (String) o));
        sorter.setComparator(1, Comparator.comparing(o -> (String) o));
        sorter.setComparator(2, Comparator.comparing(o -> (String) o, (a, b) -> {
            boolean aIsNone = a.equals("None"); // Check if Type 2 for 'a' is "None"
            boolean bIsNone = b.equals("None"); // Check if Type 2 for 'b' is "None"
            if (aIsNone && bIsNone) return 0; // If both are "None", maintain order
            if (aIsNone) return 1;  // If 'a' is "None", push it to the bottom
            if (bIsNone) return -1; // If 'b' is "None", push it to the bottom

            return a.compareTo(b); // Otherwise, sort alphabetically
        }));
    }

    // Sets column widths
    // Applys colors using TypeColorRender to type1 and/or type2 of each Pokemon
    private void setupColumnWidths() {
        int[] columnWidths = {
                NAME_COLUMN_WIDTH, TYPE1_COLUMN_WIDTH, TYPE2_COLUMN_WIDTH,
                HP_COLUMN_WIDTH, ATTACK_COLUMN_WIDTH, DEFENSE_COLUMN_WIDTH,
                SPATK_COLUMN_WIDTH, SPDEF_COLUMN_WIDTH, SPEED_COLUMN_WIDTH
        };
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Apply colors to type1 and type2 columns
        table.getColumnModel().getColumn(1).setCellRenderer(new TypeColorRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer());
    }

    // Reverts the table back to no sorts being applied
    private void resetSorting() {
        sorter.setSortKeys(null);
        table.getTableHeader().repaint();
    }

    // Adds the reset button underneath the table
    public void addResetButton() {
        JButton resetButton = new JButton("Reset Sort");
        resetButton.addActionListener(e -> resetSorting());
        add(resetButton, BorderLayout.SOUTH);
    }

/**
 * Adds a selection listener to the table.
 * When a row is selected, the corresponding Pokémon's details are displayed in the DetailsPanel.
 * */
    private void setupSelectionListener(List<Pokemon> pokemonList) {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                detailsPanel.updateDetails(pokemonList.get(row));
            }
        });
    }

    // Sets the detail panel
    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

}

