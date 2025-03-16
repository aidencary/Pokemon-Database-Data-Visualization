package GUI;

import Data.Pokemon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DetailsPanel detailsPanel;
    private TableRowSorter<DefaultTableModel> sorter;

    public TablePanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        initializeTableModel();
        populateTable(pokemonList);
        initializeTableSorter();
        setupSelectionListener(pokemonList);
        setupColumnWidths();
        add(new JScrollPane(table), BorderLayout.CENTER);
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
        for (Pokemon p : pokemonList) {
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
            if (a.equals("None")) return -1; // Move single-type Pokémon to the bottom
            if (b.equals("None")) return 1;
            return a.compareTo(b); // Alphabetical sorting for dual-type Pokémon
        }));
    }

    private void setupSelectionListener(List<Pokemon> pokemonList) {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                detailsPanel.updateDetails(pokemonList.get(row));
            }
        });
    }

    private void setupColumnWidths() {
        TableColumnModel columnModel = table.getColumnModel();
        int[] columnWidths = {150, 80, 80, 50, 60, 60, 60, 60, 60};
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        table.getColumnModel().getColumn(1).setCellRenderer(new TypeColorRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer());
    }

    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

}
