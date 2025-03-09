package GUI;

import Data.Pokemon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.List;

public class PokemonGUI {
    public PokemonGUI(List<Pokemon> pokeList) {
        final int frameWidth = 914;
        final int frameHeight = 600;

        JFrame frame = new JFrame("PokeApp");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        String[] columns = {"Pokedex ID", "Name", "Primary Type", "Secondary Type", "Base Stats", "HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed", "Gen", "Legendary"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Pokemon p : pokeList) {
            model.addRow(new Object[]{p.id(), p.name(), p.type1(), p.type2(), p.total(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed(), p.generation(), p.legendary()});
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable automatic resizing
        TableColumnModel columnModel = table.getColumnModel();

        // Adjust column widths
        int[] columnWidths = {40, 150, 80, 80, 50, 50, 60, 60, 60, 60, 60, 50, 80};
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Apply custom renderer for type columns
        table.getColumnModel().getColumn(2).setCellRenderer(new TypeColorRenderer()); // Type 1
        table.getColumnModel().getColumn(3).setCellRenderer(new TypeColorRenderer()); // Type 2


        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
    }
 }

