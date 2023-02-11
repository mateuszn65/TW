package agh.tw;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String inputFilename = "input.txt";
        String outputFilename = "output.txt";

        if(args.length == 1){
            inputFilename = args[0];
        }else if(args.length == 2){
            inputFilename = args[0];
            outputFilename = args[1];
        }
        System.out.println("Input file: " + inputFilename);
        System.out.println("Output file: " + outputFilename);
        try {
            double[][] matrix = MatrixFileReader.getMatrixFromFile(inputFilename);
//            System.out.println(Arrays.deepToString(matrix));

            double start = System.currentTimeMillis();
            GaussElimination gaussElimination = new GaussElimination(matrix);
            System.out.println("Gauss elimination took " + (System.currentTimeMillis() - start) + "ms to compute");
            MatrixFileReader.saveMatrixToFile(outputFilename, gaussElimination.getMatrix());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}