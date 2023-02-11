package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void test(int n){
        Test testStarving = new Test(n, false);
        Test testArbiter = new Test(n, true);
        System.out.println("Starving, average waiting time : " + testStarving.getAverageWaitingTime());
        System.out.println("Arbiter, average waiting time: " + testArbiter.getAverageWaitingTime());
        BarChart barChart = new BarChart();
        barChart.createJavaDataset(testStarving.getAverageWaitingTimes(), testArbiter.getAverageWaitingTimes());
        barChart.createChart(n);
        barChart.saveChart(n + " philosophers.jpeg");
//        barChart.showChart();
    }
    public static void _loadJSData(double[] averageWaitingTimes, String filename){
        BufferedReader reader = null;
        File file = new File("src/main/java/org/example/js/" + filename);
        int line = 0, i = 0;
        String text;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((text = reader.readLine()) != null) {
                if(line % 2 == 0){
                    i = Integer.parseInt(text);
                }
                else{
                    averageWaitingTimes[i] = Double.parseDouble(text);
                }
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }
    public static void loadJSData(int n){
        double[] averageWaitingTimesStarvingJS = new double[n];
        double[] averageWaitingTimesArbiterJS = new double[n];
        double[] averageWaitingTimesAsymmetricJS = new double[n];
        _loadJSData(averageWaitingTimesArbiterJS, n + "conductor.txt");
        _loadJSData(averageWaitingTimesAsymmetricJS, n + "asymmetric.txt");
        _loadJSData(averageWaitingTimesStarvingJS, n + "simultaneous.txt");

        BarChart barChart = new BarChart();
        barChart.createJSDataset(averageWaitingTimesStarvingJS, averageWaitingTimesArbiterJS, averageWaitingTimesAsymmetricJS);
        barChart.createChart(n);
        barChart.saveChart(n + " philosophersJS.jpeg");
    }
    public static void main(String[] args) {
        int n1 = 5, n2 = 10;
        test(n1);
        test(n2);
//        loadJSData(n1);
//        loadJSData(n2);
    }
}