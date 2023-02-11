package agh.tw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GaussElimination {
    private final double[][] matrix;
    private final int N;
    private final double[][] multiplications;
    private final double[] multipliers;
    public GaussElimination(double[][] matrix) throws InterruptedException {
        this.matrix = matrix;
        this.N = matrix.length;
        multipliers = new double[N];
        multiplications = new double[N][N+1];
        start();
//        System.out.println("Result:\n" + Arrays.deepToString(matrix));
        backPropagation();
//        System.out.println("Result after back propagation:\n" + Arrays.deepToString(matrix));
    }
    private void taskA(int i, int k){
        multipliers[k] = matrix[k][i] / matrix[i][i];
    }
    private void taskB(int i, int j, int k){
        multiplications[k][j] = matrix[i][j] * multipliers[k];
    }
    private void taskC(int i, int j, int k){
        if (i == j){
            matrix[k][j] = 0;
            return;
        }
        matrix[k][j] -= multiplications[k][j];
    }

    private void foatA(int i) throws InterruptedException {
        List<Thread> aThreads = new ArrayList<>();
        for (int k = i+1; k < N; k++) {
            int finalK = k;
            aThreads.add(new Thread(()-> taskA(i, finalK)));
        }
        startAndJoinThreads(aThreads);
    }

    private void foatB(int i) throws InterruptedException {
        List<Thread> bThreads = new ArrayList<>();
        for(int k=i+1; k<N; k++){
            for(int j=i; j<N+1; j++){
                int finalJ = j;
                int finalK = k;
                bThreads.add(new Thread(()-> taskB(i, finalJ, finalK)));
            }
        }
        startAndJoinThreads(bThreads);
    }
    private void foatC(int i) throws InterruptedException {
        List<Thread> cThreads = new ArrayList<>();
        for(int k=i+1; k<N; k++){
            for(int j=i; j<N+1; j++){
                int finalJ = j;
                int finalK = k;
                cThreads.add(new Thread(()-> taskC(i, finalJ, finalK)));
            }
        }
        startAndJoinThreads(cThreads);
    }

    private void start() throws InterruptedException {
        for (int i = 0; i < N -1; i++){
            foatA(i);
            foatB(i);
            foatC(i);
        }
    }

    private void backPropagation(){
        for(int i=N-1; i>=0; i--){
            for(int j=i+1; j<N; j++){
                matrix[i][N] -= matrix[i][j] * matrix[j][N];
                matrix[i][j] = 0;
            }
            matrix[i][N] /= matrix[i][i];
            matrix[i][i] = 1;
        }
    }

    private void startAndJoinThreads(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public double[][] getMatrix(){
        return matrix;
    }
    public String toString(){
        return Arrays.deepToString(matrix);
    }
}
