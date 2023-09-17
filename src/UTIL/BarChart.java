package UTIL;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class BarChart extends ApplicationFrame implements Stats{


    public BarChart(String title) {
        super(title);
    }

    private DefaultCategoryDataset createDataset(HashMap<String,String> statistics) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String state : statistics.keySet()) {

            dataset.addValue(Integer.parseInt(statistics.get(state)), state, "Livres");

        }

        return dataset;
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Livres--Statistiques",  // Chart title
                "",           // X-axis label
                "Livres",              // Y-axis label
                dataset,              // Dataset
                PlotOrientation.VERTICAL,
                true,                 // Include legend
                true,
                false
        );




        /*
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);


        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelsVisible(true);
         */

        return barChart;
    }

    @Override
    public void display(HashMap<String, String> statistics) {



        JFreeChart barChart = createBarChart(createDataset(statistics));


        // Create a ChartPanel to display the chart
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Create a JFrame to contain the ChartPanel
        JFrame frame = new JFrame("Statistiques");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Here, you can decide whether to exit the application or just hide the frame
                // To exit, you can use System.exit(0); or perform other cleanup actions
                // To hide the frame, you can use frame.setVisible(false);

                // For example, to exit the application:
                frame.setVisible(false);
            }
        });

        // Add the ChartPanel to the JFrame
        frame.getContentPane().add(chartPanel);

        // Set frame properties and make it visible
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        /*
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);

        SwingUtilities.invokeLater(() -> {
            this.pack();
            RefineryUtilities.centerFrameOnScreen(this);
            this.setVisible(true);
        });
         */

    }

}

