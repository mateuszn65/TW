package org.example;

import java.util.*;

public class Dependencies {
    private static class TaskPair{
        private Task task1;
        private Task task2;

        private TaskPair(Task task1, Task task2) {
            this.task1 = task1;
            this.task2 = task2;
        }

        @Override
        public String toString() {
            return "(" + task1.getTaskID() +", " + task2.getTaskID() + ')';
        }
    }
    private Map<Character,Task> alphabet;
    private List<TaskPair> dependentTasks;
    private List<TaskPair> independentTasks;

    public Dependencies() {
        this.alphabet = Task.getAlphabet();
        this.dependentTasks = new ArrayList<>();
        this.independentTasks = new ArrayList<>();
        calculateDependencies();
    }


    public void calculateDependencies(){
        for (Task task1: alphabet.values()) {
            for (Task task2: alphabet.values()) {
                if (task1.equals(task2) || task1.isDependent(task2)){
                    dependentTasks.add(new TaskPair(task1, task2));
                }else{
                    independentTasks.add(new TaskPair(task1, task2));
                }
            }
        }
    }

    public List<TaskPair> getDependentTasks() {
        return dependentTasks;
    }

    public List<TaskPair> getIndependentTasks() {
        return independentTasks;
    }
}
