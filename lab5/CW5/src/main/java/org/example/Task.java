package org.example;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    private static Map<Character,Task> alphabet = new HashMap<>();
    private Character taskID;
    private Character variable;
    private List<Character> formulaVariables;

    public Task(Character taskID, Character variable, List<Character> formulaVariables) {
        this.taskID = taskID;
        this.variable = variable;
        this.formulaVariables = formulaVariables;
        alphabet.put(taskID, this);
    }
    public static Map<Character,Task> getAlphabet() {
        return alphabet;
    }

    public Character getTaskID() {
        return taskID;
    }

    public Character getVariable() {
        return variable;
    }

    public List<Character> getFormulaVariables() {
        return formulaVariables;
    }

    public boolean isDependent(Task task) {
        if (this.variable == task.variable)
            return true;
        for (char c: task.formulaVariables) {
            if (this.variable == c)
                return true;
        }
        for (char c: this.formulaVariables) {
            if (task.variable == c)
                return true;
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = "(" + this.taskID +") " + this.variable;
        stringBuilder.append(string);
        stringBuilder.append(" -> ");

        for (char s: this.formulaVariables) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
