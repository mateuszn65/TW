package org.example;

import org.example.lab1.Race;



public class Main {
    public static void startRace(){
        System.out.println("\nRace start with binary semaphore");
        int wrongResults = 0;
        int tests = 100;
        for (int i = 0; i < tests; i++) {
            Race race = new Race(new BinarySemaphore());
            race.start();
            if(race.getCount() != 0){
                System.out.println("WRONG RESULT: " + race.getCount());
                wrongResults += 1;
            }
        }
        System.out.println(wrongResults + " wrong results out of " + tests);
    }
    public static void startIfRace(){
        System.out.println("\nRace start with binary semaphore with if");
        int wrongResults = 0;
        int tests = 100;
        for (int i = 0; i < tests; i++) {
            Race race = new Race(new BinarySemaphoreIf());
            race.start();

            if(race.getCount() != 0){
                System.out.println("WRONG RESULT: " + race.getCount());
                wrongResults += 1;
            }
        }
        System.out.println(wrongResults + " wrong results out of " + tests);
    }

    public static void main(String[] args) {
        startRace();
        startIfRace();
    }
}