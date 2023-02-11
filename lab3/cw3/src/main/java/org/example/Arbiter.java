package org.example;

import java.util.concurrent.Semaphore;

public class Arbiter implements Runnable{
    private Semaphore fork1;
    private Semaphore fork2;
    private Semaphore arbiter;
    private int id;
    private int timesToFinishMeal;
    private int timesAlreadyEaten;
    private int sumWaitingTime;
    private double[] averageWaitingTimes;
    public Arbiter(Semaphore fork, Semaphore fork1, int i, int n_meals, double[] averageWaitingTimes, Semaphore arbiter) {
        this.fork1 = fork;
        this.fork2 = fork1;
        this.id = i;
        this.timesToFinishMeal = n_meals;
        this.timesAlreadyEaten = 0;
        this.sumWaitingTime = 0;
        this.averageWaitingTimes = averageWaitingTimes;
        this.arbiter = arbiter;
    }

    @Override
    public void run() {
        long startTime, endTime, waitingTime;
        startTime = System.currentTimeMillis();
        while (this.timesAlreadyEaten < this.timesToFinishMeal){
            try {
                this.arbiter.acquire();
                this.fork1.acquire();
                this.fork2.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //eat
            endTime = System.currentTimeMillis();
//            System.out.println("Philosopher "+ this.id + ", eating for the " + (this.timesAlreadyEaten + 1) + " time");
            waitingTime = endTime - startTime;
            this.sumWaitingTime += waitingTime;
            this.timesAlreadyEaten++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startTime = System.currentTimeMillis();


            this.fork2.release();
            this.fork1.release();
            this.arbiter.release();
        }
        this.averageWaitingTimes[this.id] = this.sumWaitingTime / (double) this.timesToFinishMeal;

    }
}
