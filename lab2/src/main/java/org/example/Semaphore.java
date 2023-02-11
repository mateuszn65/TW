package org.example;

public class Semaphore implements ISemaphore{
    private int counter;
    private ISemaphore semaphoreStatus;
    private ISemaphore semaphoreCounter;
    public Semaphore(int maxCount){
        if (maxCount <= 0)
            this.counter = 1;
        else
            this.counter = maxCount;
        this.semaphoreStatus = new BinarySemaphore();
        this.semaphoreCounter = new BinarySemaphore();
    }
    @Override
    public void lock(){
        this.semaphoreCounter.lock();
        this.semaphoreStatus.lock();
        this.counter--;
        if (this.counter > 0){
            this.semaphoreStatus.unlock();
        }

        this.semaphoreCounter.unlock();
    }

    @Override
    public void unlock() {
        this.semaphoreCounter.lock();
        this.counter++;
        if (this.counter > 0){
            this.semaphoreStatus.unlock();
        }

        this.semaphoreCounter.unlock();
    }
}
