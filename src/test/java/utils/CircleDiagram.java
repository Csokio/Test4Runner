package utils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYZDataset;
import org.testng.TestRunner;
import org.jfree.chart.plot.PiePlot;


import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CircleDiagram {

    private final int passedTest;
    private final int failedTest;
    public CircleDiagram(int passedTest, int failedTest, int location) {
        this.passedTest = passedTest;
        this.failedTest = failedTest;

        double success = 100.0 / (passedTest+failedTest) * passedTest;
        double failed =  100.0 / (passedTest+failedTest) * failedTest;

        Map<String, Double> inputData = new HashMap<>();
        inputData.put("Sikeres Tesztek", success);
        inputData.put("Sikertelen Tesztek", failed);


        // Create a dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        inputData.forEach(dataset::setValue);


        // Create the chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Test Cases",
                dataset,
                true,
                true,
                false
        );

        // Customize the appearance of the chart
        chart.setBackgroundPaint(Color.white);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Sikeres Tesztek", Color.GREEN);
        plot.setSectionPaint("Sikertelen Tesztek", Color.RED);

        // Display the chart in a frame
        JFrame frame = new JFrame("TestRunner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        frame.setLocation(location, 0);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


}

