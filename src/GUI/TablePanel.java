package GUI;

import Data.Pokemon;
import Filter.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TablePanel extends JPanel {
    // String constants
    public static final String SHOW_LEGENDARY = "Show Only Legendary";

    // Integer constants for column widths
    public static final int NAME_COLUMN_WIDTH = 150;
    public static final int TYPE1_COLUMN_WIDTH = 80;
    public static final int TYPE2_COLUMN_WIDTH = 80;
    public static final int HP_COLUMN_WIDTH = 50;
    public static final int ATTACK_COLUMN_WIDTH = 60;
    public static final int DEFENSE_COLUMN_WIDTH = 60;
    public static final int SPATK_COLUMN_WIDTH = 60;
    public static final int SPDEF_COLUMN_WIDTH = 60;
    public static final int SPEED_COLUMN_WIDTH = 60;

    // Fields for components and data
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private JComboBox<String> typeFilter;
    private JComboBox<String> generationFilter;
    private JCheckBox legendaryFilter;
    private StatsPanel statsPanel;
    private DetailsPanel detailsPanel;
    private TypeChartPanel typeChartPanel;
    private ListSelectionListener rowSelectionListener;
    private List<FilterObserver> observers = new ArrayList<>();

    public TablePanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        initializeTableModel();         // Setup the table and its columns
        initializeFilters(pokemonList);            // Setup filter controls
        populateTable(pokemonList); // Populate with all Pokémon
        initializeTableSorter();        // Setup sorting for each column
        setupSelectionListener(pokemonList); // Setup row selection listener
        setupColumnWidths();            // Apply widths and type color renderer
        add(new JScrollPane(table), BorderLayout.CENTER); // Scrollable table
        addResetButton();               // Add sort reset button
    }

    // Creates combo boxes and checkboxes to filter the Pokémon
    private void initializeFilters(List<Pokemon> pokemonList) {
        JPanel filterPanel = new JPanel(new FlowLayout());

        // Dropdown for filtering by primary/secondary type
        typeFilter = new JComboBox<>(new String[]{
                "All", "Fire", "Water", "Grass", "Electric", "Ice", "Fighting", "Poison", "Ground",
                "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
        });

        // Dropdown for filtering by generation
        generationFilter = new JComboBox<>(new String[]{
                "All", "Generation 1", "Generation 2", "Generation 3", "Generation 4", "Generation 5", "Generation 6"
        });

        // Checkbox to filter for legendary Pokémon only
        legendaryFilter = new JCheckBox(SHOW_LEGENDARY);

        // Button to apply selected filters
        JButton applyFiltersBtn = new JButton("Apply Filters");
        applyFiltersBtn.addActionListener(e -> applyFilters(pokemonList));

        // Add all filter components to the panel
        filterPanel.add(new JLabel("Filter by Type:"));
        filterPanel.add(typeFilter);
        filterPanel.add(new JLabel("Filter by Generation:"));
        filterPanel.add(generationFilter);
        filterPanel.add(legendaryFilter);
        filterPanel.add(applyFiltersBtn);

        add(filterPanel, BorderLayout.NORTH);
    }

    // Applies filters and updates all connected panels
    private void applyFilters(List<Pokemon> pokemonList) {
        String selectedType = (String) typeFilter.getSelectedItem();
        String selectedGeneration = (String) generationFilter.getSelectedItem();
        boolean showOnlyLegendary = legendaryFilter.isSelected();
        //String selectedGenNumber = selectedGeneration.equals("All") ? "All" : selectedGeneration.split(" ")[1];

        // Create strategy list
        List<PokemonFilterStrategy> filters = new ArrayList<>();
        if (!selectedType.equals("All")) filters.add(new TypeFilter(selectedType));
        if (!selectedGeneration.equals("All")) filters.add(new GenerationFilter(selectedGeneration.split(" ")[1]));
        if (showOnlyLegendary) filters.add(new LegendaryFilter());

        // Apply strategies
        List<Pokemon> filtered = FilterUtils.applyFilters(pokemonList, filters);

        // Clear and repopulate table
        model.setRowCount(0);
        for (Pokemon p : filtered) {
            model.addRow(new Object[]{p.name(), p.type1(), p.type2(),
                    p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()});
        }

        // Update connected panels (optional legacy updates)
        if (detailsPanel != null) setupSelectionListener(filtered);

        // Notify observer panels
        notifyObservers(filtered);

        // Update stats and type chart
        if (statsPanel != null) statsPanel.updateStats(filtered);
        if (typeChartPanel != null) typeChartPanel.updateChart(filtered);
        if (detailsPanel != null) setupSelectionListener(filtered);
    }

    // Sets up the columns for the table
    private void initializeTableModel() {
        String[] columns = {"Name", "Type 1", "Type 2", "HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    // Populates the table with Pokémon data
    private void populateTable(List<Pokemon> pokemonList) {
        for (Pokemon p : pokemonList) {
            model.addRow(new Object[]{
                    p.name(), p.type1(), p.type2(),
                    p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()
            });
        }
    }

    // Sets up sorting for table columns
    private void initializeTableSorter() {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Sorting for numerical columns (descending)
        for (int i = 3; i <= 8; i++) {
            sorter.setComparator(i, Comparator.comparingInt(o -> (int) o).reversed());
        }

        // Alphabetical sorting for Name, Type1, and Type2
        sorter.setComparator(0, Comparator.comparing(o -> (String) o));
        sorter.setComparator(1, Comparator.comparing(o -> (String) o));
        sorter.setComparator(2, Comparator.comparing(o -> (String) o, (a, b) -> {
            boolean aNone = a.equals("None");
            boolean bNone = b.equals("None");
            if (aNone && bNone) return 0;
            if (aNone) return 1;
            if (bNone) return -1;
            return a.compareTo(b);
        }));
    }

    // Sets the preferred width and custom renderer for type columns
    private void setupColumnWidths() {
        int[] widths = {
                NAME_COLUMN_WIDTH, TYPE1_COLUMN_WIDTH, TYPE2_COLUMN_WIDTH,
                HP_COLUMN_WIDTH, ATTACK_COLUMN_WIDTH, DEFENSE_COLUMN_WIDTH,
                SPATK_COLUMN_WIDTH, SPDEF_COLUMN_WIDTH, SPEED_COLUMN_WIDTH
        };

        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        table.getColumnModel().getColumn(1).setCellRenderer(new TypeColorRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer());
    }

    // Resets table sorting to original state
    private void resetSorting() {
        sorter.setSortKeys(null);
        table.getTableHeader().repaint();
    }

    // Adds a reset button to the bottom of the table panel
    public void addResetButton() {
        JButton resetButton = new JButton("Reset Sort");
        resetButton.addActionListener(e -> resetSorting());
        add(resetButton, BorderLayout.SOUTH);
    }

    // Connects the DetailsPanel to display data on row selection
    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

    // Connects the StatsPanel to update with filters
    public void setStatsPanel(StatsPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    // Connects the TypeChartPanel to update with filters
    public void setTypeChartPanel(TypeChartPanel chartPanel) {
        this.typeChartPanel = chartPanel;
    }

    private void setupSelectionListener(List<Pokemon> currentList) {
        // Remove the old listener if it exists
        if (rowSelectionListener != null) {
            table.getSelectionModel().removeListSelectionListener(rowSelectionListener);
        }

        // Clear any previous selection
        table.clearSelection();

        // Ensure proper selection behavior
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        // Create a new listener and store it
        rowSelectionListener = e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1 && detailsPanel != null) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                if (row >= 0 && row < currentList.size()) {
                    detailsPanel.updateDetails(currentList.get(row));
                }
            }
        };

        // Add it to the model
        table.getSelectionModel().addListSelectionListener(rowSelectionListener);
    }

    // Adds a FilterObserver
    public void addFilterObserver(FilterObserver observer) {
        observers.add(observer);
    }

    // Notifies the observer when a filter is applied
    private void notifyObservers(List<Pokemon> filteredList) {
        for (FilterObserver o : observers) {
            o.onFilterUpdate(filteredList);
        }
    }

}
