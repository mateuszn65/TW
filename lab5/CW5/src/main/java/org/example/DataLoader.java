package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {
    private List<String> tasks;
    private String alphabet;
    private String word;

    public List<String> getTasks() {
        return tasks;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public String getWord() {
        return word;
    }

    public DataLoader(String filename) throws Exception {
        tasks = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                if (tryMatchTask(data)){
                    tasks.add(data);
                    continue;
                }
                if (alphabet == null && tryMatchAlphabet(data)){
                    alphabet = data;
                    continue;
                }
                if (word == null && tryMatchWord(data)){
                    word = data;
                    continue;
                }
                System.out.println(data);
            }
            bufferedReader.close();
            if (alphabet == null)
                throw new Exception("Incorrect data, provide alphabet");
            if (word == null)
                throw new Exception("Incorrect data, provide word");
            if (alphabet.split(",").length != tasks.size())
                throw new Exception("Incomplete data, alphabet contains " + alphabet.split(",").length +
                        " tasks, but read " + tasks.size());

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private boolean tryMatchWord(String string) {
        Pattern pattern = Pattern.compile("^w = [a-z]+$");
        Matcher matcher = pattern.matcher(string);
        boolean matchFound = matcher.find();
        System.out.println(string);
        if(matchFound) {
            System.out.println("word Match found");
        } else {
            System.out.println("word Match not found");
        }
        return matchFound;
    }

    private boolean tryMatchAlphabet(String string) {
        Pattern pattern = Pattern.compile("^A = [{](([a-z]+), )+([a-z]+)[}]$");
        Matcher matcher = pattern.matcher(string);
        boolean matchFound = matcher.find();
        System.out.println(string);
        if(matchFound) {
            System.out.println("alphabet Match found");
        } else {
            System.out.println("alphabet Match not found");
        }
        return matchFound;
    }

    private boolean tryMatchTask(String string) {
        Pattern pattern = Pattern.compile("^[(][a-z]+[)] [a-z]+ := ");
        Matcher matcher = pattern.matcher(string);
        boolean matchFound = matcher.find();
        System.out.println(string);
        if(matchFound) {
            System.out.println("task Match found");
        } else {
            System.out.println("task Match not found");
        }
        return matchFound;
    }

}
