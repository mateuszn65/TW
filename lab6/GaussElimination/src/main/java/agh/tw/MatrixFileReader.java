package agh.tw;

import java.io.*;

public class MatrixFileReader {
    public static double[][] getMatrixFromFile(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String data = bufferedReader.readLine();
        int N = Integer.parseInt(data);
        double[][] matrix = new double[N][N + 1];
        String[] row;
        int i = 0, j;
        while ((data = bufferedReader.readLine()) != null && i < N) {
            row = data.split(" ");
            j = 0;
            while (j < row.length && j < N) {
                matrix[i][j] = Double.parseDouble(row[j]);
                j++;
            }
            i++;
        }

        if (data != null){
            row = data.split(" ");
            j = 0;
            while (j < row.length && j < N) {
                matrix[j][N] = Double.parseDouble(row[j]);
                j++;
            }
        }
        bufferedReader.close();
        return matrix;
    }
    public static void saveMatrixToFile(String filename, double[][] matrix) throws IOException {
        int N = matrix.length;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        bufferedWriter.write(String.valueOf(N) + '\n');

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                bufferedWriter.write(String.valueOf(matrix[i][j]) + ' ');
            }
            bufferedWriter.write("\n");
        }

        for(int i=0; i<N; i++){
            bufferedWriter.write(String.valueOf(matrix[i][N]) + ' ');
        }

        bufferedWriter.close();
    }
}
