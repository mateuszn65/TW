package org.example;

public class BinarySemaphore implements ISemaphore{
    private boolean unlocked;
    public BinarySemaphore(){
        this.unlocked = true;
    }

    public synchronized void lock() {
        while (!this.unlocked)
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        this.unlocked = false;
        notifyAll();
    }

    public synchronized void unlock() {
        this.unlocked = true;
        notifyAll();
    }
}
