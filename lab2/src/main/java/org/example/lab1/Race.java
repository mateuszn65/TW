package org.example.lab1;

import org.example.ISemaphore;

import java.util.ArrayList;
import java.util.List;

public class Race {
    int reps = 1000;
    int threadsCount = 4;
    Counter counter;
    public Race(ISemaphore semaphore){
        this.counter = new Counter(semaphore);
    }
    public void start(){

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount / 2    ; i++) {
            Thread incrementorThread = new Thread(new Incrementor(reps, this.counter));
            Thread decrementThread = new Thread(new Decrymentor(reps, this.counter));
            threads.add(incrementorThread);
            threads.add(decrementThread);
        }

        threads.forEach(Thread::start);

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("Counter: " + getCount());
    }
    public int getCount(){
        return this.counter.getCount();
    }
}
