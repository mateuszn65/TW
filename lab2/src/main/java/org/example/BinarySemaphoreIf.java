package org.example;

public class BinarySemaphoreIf implements ISemaphore{
    private boolean unlocked;
    public BinarySemaphoreIf(){
        this.unlocked = true;
    }

    public synchronized void lock() {
        if (!this.unlocked)
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
