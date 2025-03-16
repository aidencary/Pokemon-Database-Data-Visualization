
package GUI;

import Data.Pokemon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.jfree.chart.ChartFactory.createPieChart;

/*
* Adding libraries to project:
* File -> New Project. Next I clicked File -> Project Structure...
* and selected the Libraries entry then clicked the + to add
* both jfree.jcommon-1.0.24.jar and j.free.jfreechart-1.0.19.jar
* You can search "jcommon" and "jfreechart" and click the magnifying glass
* and scroll until you see these libraries
*/

class TypeChartPanel extends JPanel {
    // Integer constants
    public static final int DIMENSION_WIDTH = 400;
    public static final int DIMENSION_HEIGHT = 400;

    public TypeChartPanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        JFreeChart pieChart = createPieChart(pokemonList);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(DIMENSION_WIDTH, DIMENSION_HEIGHT)); // Ensure visibility
        add(chartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createPieChart(List<Pokemon> pokemonList) {
        // Stream for counting the number of each type in the list
        Map<String, Long> typeCounts = pokemonList.stream()
                .flatMap(p -> Stream.of(p.type1(), p.type2()))
                .filter(type -> !type.equals("None"))
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        // Creates DefaultPieDataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        typeCounts.forEach(dataset::setValue);

        // Creates chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Pokémon Type Distribution",
                dataset,
                true,
                true,
                false
        );

        // Create PiePlot
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelShadowPaint(Color.LIGHT_GRAY);
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}: {1}")); // Adds the number of each type in the table to the label

        // Uses TypeColorRenderer to render colors in the PiePlot
        for (Map.Entry<String, Color> entry : TypeColorRenderer.getTypeColors().entrySet()) {
            plot.setSectionPaint(entry.getKey(), entry.getValue());
        }

        return chart;
    }
}

