package GUI;

import Data.Pokemon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DetailsPanel detailsPanel;

    public TablePanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());

        String[] columns = {"Name", "Type 1", "Type 2", "HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
    };
        for (Pokemon p : pokemonList) {
            model.addRow(new Object[]{p.name(), p.type1(), p.type2(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed()});
        }

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                detailsPanel.updateDetails(pokemonList.get(row));
            }
        });

        TableColumnModel columnModel = table.getColumnModel();
        int[] columnWidths = {150, 80, 80, 50, 60, 60, 60, 60, 60};
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        table.getColumnModel().getColumn(1).setCellRenderer(new TypeColorRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

}
