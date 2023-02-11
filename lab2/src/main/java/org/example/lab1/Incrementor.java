package org.example.lab1;
public class Incrementor implements Runnable{
    int reps;
    Counter counter;
    public Incrementor(int reps, Counter counter) {
        this.reps = reps;
        this.counter = counter;
    }

    @Override
    public void run(){
        for (int i = 0; i < reps; i++) {
            this.counter.increment();
        }
    }
}
