package GUI;

import Data.Pokemon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PokemonGUI {
    public PokemonGUI(List<Pokemon> pokeList) {
        final int frameWidth = 1500;
        final int frameHeight = 600;

        JFrame frame = new JFrame("PokeApp");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        String[] columns = {"Pokedex ID", "Name", "Primary Type", "Secondary Type", "Base Stats", "HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed", "Gen", "Legendary"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Pokemon p : pokeList) {
            model.addRow(new Object[]{p.id(), p.name(), p.type1(), p.type2(), p.total(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed(), p.generation(), p.legendary()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
    }
}
