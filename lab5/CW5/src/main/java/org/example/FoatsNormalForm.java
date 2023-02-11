package org.example;

import java.util.ArrayList;
import java.util.List;

public class FoatsNormalForm {
    private List<List<Task>> fnf;
    public FoatsNormalForm(String word){
        fnf = new ArrayList<>();
        calculateFoatsNormalForm(word.toCharArray());
    }
    private void calculateFoatsNormalForm(char[] word){
        addLayer(getTask(word[0]));
        for(int j = 1; j < word.length; j++){
            int i = fnf.size();
            Task task = getTask(word[j]);
            while (i > 0){
                if (isLayerDependent(task, i - 1)){
                    if (i == fnf.size()){
                        addLayer(task);
                    }else{
                        fnf.get(i).add(task);
                    }
                    break;
                }
                i -= 1;
            }
            if (i == 0){
                fnf.get(i).add(task);
            }
        }
    }

    private boolean isLayerDependent(Task task, int i) {
        for(Task layerTask: fnf.get(i)){
            if (task.isDependent(layerTask)){
                return true;
            }
        }
        return false;
    }

    private void addLayer(Task task){
        List<Task> layer = new ArrayList<>();
        layer.add(task);
        fnf.add(layer);
    }
    private Task getTask(char c){
        return Task.getAlphabet().get(c);
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Task> tasks: fnf){
            stringBuilder.append("[");
            for (Task task: tasks){
                stringBuilder.append(task.getTaskID());
            }
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}
