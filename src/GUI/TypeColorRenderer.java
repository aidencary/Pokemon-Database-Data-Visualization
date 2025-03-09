package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TypeColorRenderer  extends DefaultTableCellRenderer {
    private static final Map<String, Color> typeColors = new HashMap<>();

    static {
        // Used AI to generate the colors
        typeColors.put("Fire", Color.RED);
        typeColors.put("Water", new Color(173, 247, 255));
        typeColors.put("Grass", Color.GREEN);
        typeColors.put("Electric", Color.YELLOW);
        typeColors.put("Ice", Color.CYAN);
        typeColors.put("Fighting", new Color(184, 69, 45));
        typeColors.put("Poison", new Color(153, 51, 153));
        typeColors.put("Ground", new Color(181, 140, 83));
        typeColors.put("Flying", new Color(135, 206, 250));
        typeColors.put("Psychic", new Color(255, 105, 180));
        typeColors.put("Bug", new Color(154, 205, 50));
        typeColors.put("Rock", new Color(169, 169, 169));
        typeColors.put("Ghost", new Color(123, 104, 238));
        typeColors.put("Dragon", new Color(75, 0, 100));
        typeColors.put("Dark", new Color(90, 90, 90));
        typeColors.put("Steel", new Color(192, 192, 192));
        typeColors.put("Fairy", new Color(255, 182, 193));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null) {
            Color c = typeColors.getOrDefault(value.toString(), Color.WHITE);
            cell.setBackground(c);
            cell.setForeground(Color.BLACK);
        }
        return cell;
    }
}
