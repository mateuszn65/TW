package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try{
            String filename = "data_input1.txt";
            if (args.length > 0){
                filename = args[0];
            }
            DataLoader dataLoader = new DataLoader("input/" + filename);
            DataSaver dataSaver = new DataSaver();
            dataSaver.add("Dla danych:\n");
            for (String stringTask: dataLoader.getTasks()) {
                dataSaver.add(stringTask);
                dataSaver.add("\n");
                char[] chars = stringTask.toCharArray();
                Character taskID = chars[1];
                Character variable = chars[4];
                List<Character> formulaVariables = new ArrayList<>();
                for (int i=9; i < chars.length; i++) {
                    if (Pattern.compile("[a-z]").matcher(chars[i] + "").matches()){
                        Character formulaVariable = chars[i];
                        formulaVariables.add(formulaVariable);
                    }
                }
                Task task = new Task(taskID, variable, formulaVariables);
            }

            if (dataLoader.getAlphabet().split(",").length != Task.getAlphabet().size())
                throw new Exception("Incorrect data, alphabet contains " +
                        dataLoader.getAlphabet().split(",").length +
                        " tasks, but read " + Task.getAlphabet().size());

            dataSaver.add(dataLoader.getAlphabet());
            dataSaver.add("\n");
            dataSaver.add(dataLoader.getWord());
            dataSaver.add("\n");

            Dependencies dependencies = new Dependencies();
            String word = dataLoader.getWord().substring(4);
            FoatsNormalForm fnf = new FoatsNormalForm(word);
            DependencyGraph dependencyGraph = new DependencyGraph(word);

            dataSaver.add("\nWyniki:");
            dataSaver.add("\nD = ");
            dataSaver.add(dependencies.getDependentTasks().toString());
            dataSaver.add("\nI = ");
            dataSaver.add(dependencies.getIndependentTasks().toString());
            dataSaver.add("\nFNF([w]) = ");
            dataSaver.add(fnf.toString());
            dataSaver.add("\n");
            dataSaver.add(dependencyGraph.toDot());

            System.out.println("\n\nWyniki:");
            System.out.println("D = " + dependencies.getDependentTasks());
            System.out.println("I = " + dependencies.getIndependentTasks());
            System.out.println("w = " + word);
            System.out.println("FNF([w]) = " + fnf);

            System.out.println(dependencyGraph.toDot());

            dependencyGraph.toPNG("output/graphFrom_"+filename.split("\\.")[0]);
            dataSaver.save("output/resultFrom_"+filename);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}