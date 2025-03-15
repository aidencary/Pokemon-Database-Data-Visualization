package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private JTextArea detailsArea;

    public DetailsPanel() {
        setLayout(new BorderLayout());
        detailsArea = new JTextArea(5, 50);
        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }

    public void updateDetails(Pokemon p) {
        detailsArea.setText(String.format("Name: %s\nType 1: %s\nType 2: %s\nTotal: %d\nHP: %d\nAttack: %d\nDefense: %d\nSp. Atk: %d\nSp. Def: %d\nSpeed: %d\nGeneration: %d\nLegendary: %b",
                p.name(), p.type1(), p.type2(), p.total(), p.hp(), p.attack(), p.defense(), p.spAtk(), p.spDef(), p.speed(), p.generation(), p.legendary()));
    }

}
