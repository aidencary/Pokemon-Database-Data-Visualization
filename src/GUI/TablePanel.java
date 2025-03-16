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

    private void initializeFilters() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        typeFilter = new JComboBox<>(new String[]{"All", "Fire", "Water", "Grass", "Electric", "Ice", "Fighting", "Poison", "Ground",
                "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"});
        legendaryFilter = new JCheckBox("Show Only Legendary");
        generationFilter = new JComboBox<>(new String[]{"All", "Generation 1", "Generation 2", "Generation 3", "Generation 4", "Generation 5", "Generation 6"});

        typeFilter.addActionListener(e -> applyFilters());
        legendaryFilter.addActionListener(e -> applyFilters());
        generationFilter.addActionListener(e -> applyFilters());

        filterPanel.add(new JLabel("Filter by Type:"));
        filterPanel.add(typeFilter);
        filterPanel.add(legendaryFilter);
        filterPanel.add(new JLabel("Filter by Generation:"));
        filterPanel.add(generationFilter);

        add(filterPanel, BorderLayout.NORTH);
    }

    private void applyFilters() {
        String selectedType = (String) typeFilter.getSelectedItem();
        boolean showOnlyLegendary = legendaryFilter.isSelected();
        String selectedGeneration = (String) generationFilter.getSelectedItem();

        // Extract generation number from selected filter (e.g., "Generation 1" → "1")
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
            //if (!selectedGeneration.equals("All") && !("Generation " + p.generation()).equals(selectedGeneration)) {
            if (!selectedGenNumber.equals("All") && !pokemonGenNumber.equals(selectedGenNumber)) {
            continue;
            }

            // Add filtered data to the table model
            model.addRow(new Object[]{p.name(), p.type1(), p.type2(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()});
        }
    }

    private void initializeTableModel() {
        String[] columns = {"Name", "Type 1", "Type 2", "HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void populateTable(List<Pokemon> pokemonList) {
        fullPokemonData.clear(); // Clear any existing data
        fullPokemonData.addAll(pokemonList); // Store full data for filtering

        for (Pokemon p : pokemonList) {
            // Add only visible columns to the table model
            model.addRow(new Object[]{p.name(), p.type1(), p.type2(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()});
        }
    }

    private void initializeTableSorter() {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        for (int i = 3; i <= 8; i++) {
            sorter.setComparator(i, Comparator.comparingInt(o -> (int) o).reversed());
        }
        sorter.setComparator(0, Comparator.comparing(o -> (String) o));
        sorter.setComparator(1, Comparator.comparing(o -> (String) o));
        sorter.setComparator(2, Comparator.comparing(o -> (String) o, (a, b) -> {
            boolean aIsNone = a.equals("None");
            boolean bIsNone = b.equals("None");

            if (aIsNone && bIsNone) return 0;
            if (aIsNone) return 1;
            if (bIsNone) return -1;

            return a.compareTo(b);
        }));
    }

    private void setupColumnWidths() {
        int[] columnWidths = {150, 80, 80, 50, 60, 60, 60, 60, 60};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        table.getColumnModel().getColumn(1).setCellRenderer(new TypeColorRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer());
    }

    private void resetSorting() {
        sorter.setSortKeys(null);
        table.getTableHeader().repaint();
    }

    public void addResetButton() {
        JButton resetButton = new JButton("Reset Sort");
        resetButton.addActionListener(e -> resetSorting());
        add(resetButton, BorderLayout.SOUTH);
    }

    private void setupSelectionListener(List<Pokemon> pokemonList) {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                detailsPanel.updateDetails(pokemonList.get(row));
            }
        });
    }

    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

}

