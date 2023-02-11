package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    //not synchornized 510084/-1297751/ 882561
    public static void crashTest(){
        Counter counter = new Counter();
        for (int i = 0; i < 100000; i ++){
            System.out.println("Threads: " + i);
            if (i % 2 == 0){
                new Thread(() ->{
                    counter.inc();
                });
            }else{
                new Thread(() ->{
                    counter.dec();
                });
            }
        }
    }
    public static void test() throws InterruptedException {
        Counter counter = new Counter();
        Thread thread1 = new Thread(() ->{
            for ( int i = 0; i < 10000000 ; i++ ) counter.inc();
        });
        Thread thread2 = new Thread(() ->{
            for ( int i = 0; i < 10000000 ; i++ ) counter.dec();
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("Counter value: " + counter.i);
    }
    public static void main(String[] args) throws InterruptedException {
//        test();
        crashTest();
    }
}