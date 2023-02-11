package org.example.lab1;

import org.example.ISemaphore;


public class Counter {
    private int count;
    private ISemaphore semaphore;

    public Counter(ISemaphore semaphore){
        this.count = 0;
        this.semaphore = semaphore;
    }

    public void increment() {
        semaphore.lock();
        count++;
        semaphore.unlock();
    }

    public void decrement() {
        semaphore.lock();
        count--;
        semaphore.unlock();
    }

    public int getCount() {
        return count;
    }
}
