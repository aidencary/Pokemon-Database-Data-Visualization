package GUI;

import Data.Pokemon;
import Filter.FilterObserver;
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

class TypeChartPanel extends JPanel implements FilterObserver {
    // Integer constants
    public static final int DIMENSION_WIDTH = 400;
    public static final int DIMENSION_HEIGHT = 400;

    private DefaultPieDataset dataset;
    private PiePlot plot;

    public TypeChartPanel(List<Pokemon> pokemonList) {
        setLayout(new BorderLayout());

        dataset = new DefaultPieDataset();
        updateDataset(pokemonList);

        JFreeChart chart = ChartFactory.createPieChart(
                "Pokémon Type Distribution",
                dataset,
                true,
                true,
                false
        );

        plot = (PiePlot) chart.getPlot();
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.BLACK);
        plot.setLabelShadowPaint(Color.LIGHT_GRAY);
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}: {1}"));

        for (Map.Entry<String, Color> entry : TypeColorRenderer.getTypeColors().entrySet()) {
            plot.setSectionPaint(entry.getKey(), entry.getValue());
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(DIMENSION_WIDTH, DIMENSION_HEIGHT));
        add(chartPanel, BorderLayout.CENTER);
    }

    // Public method to refresh the chart when filters are applied
    public void updateChart(List<Pokemon> filteredList) {
        updateDataset(filteredList);
    }

    // Replaces data in the dataset based on filtered Pokémon
    private void updateDataset(List<Pokemon> pokemonList) {
        dataset.clear();

        Map<String, Long> typeCounts = pokemonList.stream()
                .flatMap(p -> Stream.of(p.type1(), p.type2()))
                .filter(type -> !type.equals("None"))
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        typeCounts.forEach(dataset::setValue);
    }


    // Updates the chart panel when a filter is applied
    @Override
    public void onFilterUpdate(List<Pokemon> filteredList) {
        updateChart(filteredList); // or updateChart(filteredList) in TypeChartPanel
    }

}