package org.example;

import java.util.concurrent.Semaphore;

import static java.lang.Math.round;

public class Test {
    private int n;
    int n_meals;
    private Thread[] philosophers;
    private Semaphore[] forks;
    private double[] averageWaitingTimes;

    public Test(int n, boolean arbiter){
        this(n, arbiter, 50);
    }

    public Test(int n, boolean arbiter, int n_meals){
        this.n_meals = n_meals;
        this.n = n;
        philosophers = new Thread[n];
        forks = new Semaphore[n];
        averageWaitingTimes = new double[n];
        for(int i=0; i<n; i++){
            forks[i] = new Semaphore(1);
        }
        if (arbiter)
            initArbiter();
        else
            initStarving();

        for(int i=0; i<n; i++){
            philosophers[i].start();
        }

        try{
            for(int i=0; i<n; i++){
                philosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Philosopher nr: " + i + " waited on average: " + averageWaitingTimes[i] + " milliseconds");
        }
    }

    private void initStarving(){
        for(int i=0; i<n; i++){
            philosophers[i] = new Thread(new PhilosopherStarving(forks[i], forks[(i+1) % n], i, n_meals, averageWaitingTimes));
        }
    }
    private void initArbiter(){
        Semaphore arbiter = new Semaphore(n - 1);
        for(int i=0; i<n; i++){
            philosophers[i] = new Thread(new Arbiter(forks[i], forks[(i+1) % n], i, n_meals, averageWaitingTimes, arbiter));
        }
    }

    public double[] getAverageWaitingTimes() {
        return averageWaitingTimes;
    }
    public double getAverageWaitingTime(){
        double sum = 0;
        for(double avg : averageWaitingTimes){
            sum += avg;
        }
        return round(sum / (double) n);
    }
}
