package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils ;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;

public class BarChart extends ApplicationFrame {
    private DefaultCategoryDataset dataset;

    private JFreeChart chart;
    private int width = 640;
    private int height = 480;
    public BarChart() {
        super("Bar Chart");
    }
    public void createChart(int n){
        chart = ChartFactory.createBarChart(
                "Average times for " + n + " philosophers",
                "philosopher id",
                "time [ms]",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

    }
    public void createJavaDataset(double[] averageWaitingTimesStarving, double[] averageWaitingTimesArbiter){
        dataset = new DefaultCategoryDataset();
        for(int i=0; i<averageWaitingTimesStarving.length; i++){
            dataset.addValue(averageWaitingTimesStarving[i], "Starving", String.valueOf(i));
        }

        for(int i=0; i<averageWaitingTimesArbiter.length; i++){
            dataset.addValue(averageWaitingTimesArbiter[i], "Arbiter", String.valueOf(i));
        }
    }
    public void createJSDataset(double[] averageWaitingTimesStarvingJS, double[] averageWaitingTimesArbiterJS, double[] averageWaitingTimesAsymmetricJS){
        dataset = new DefaultCategoryDataset();
        for(int i=0; i<averageWaitingTimesStarvingJS.length; i++){
            dataset.addValue(averageWaitingTimesStarvingJS[i], "Starving", String.valueOf(i));
        }

        for(int i=0; i<averageWaitingTimesArbiterJS.length; i++){
            dataset.addValue(averageWaitingTimesArbiterJS[i], "Arbiter", String.valueOf(i));
        }

        for(int i=0; i<averageWaitingTimesAsymmetricJS.length; i++){
            dataset.addValue(averageWaitingTimesAsymmetricJS[i], "Asymmetric", String.valueOf(i));
        }
    }
    public void showChart(){
        ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize(new java.awt.Dimension( width , height ) );
        setContentPane( chartPanel );
        this.pack( );
        UIUtils.centerFrameOnScreen( this );
        this.setVisible( true );
    }
    public void saveChart(String fileName){
        File file = new File(fileName);
        try {
            ChartUtils.saveChartAsJPEG(file, chart, width, height);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
