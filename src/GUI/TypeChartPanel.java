
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
    public TypeChartPanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());
        JFreeChart pieChart = createPieChart(pokemonList);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(400, 400)); // Ensure visibility
        add(chartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createPieChart(List<Pokemon> pokemonList) {
        Map<String, Long> typeCounts = pokemonList.stream()
                .flatMap(p -> Stream.of(p.type1(), p.type2()))
                .filter(type -> !type.equals("None"))
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        DefaultPieDataset dataset = new DefaultPieDataset();
        typeCounts.forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Pokémon Type Distribution",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelShadowPaint(Color.LIGHT_GRAY);
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}: {1}"));



        // Use TypeColorRenderer colors
        for (Map.Entry<String, Color> entry : TypeColorRenderer.getTypeColors().entrySet()) {
            plot.setSectionPaint(entry.getKey(), entry.getValue());
        }

        return chart;
    }
}

