package org.example;


import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.model.Factory.*;

public class DependencyGraph {
    private static class Vertex{
        public int id;
        public char label;
        public Task task;
        public String pngLabel;
        private Vertex(int id, char label, String pngLabel){
            this.id = id;
            this.pngLabel = pngLabel;
            this.label = label;
            this.task = Task.getAlphabet().get(label);
        }
    }
    private static class Edge{
        public Vertex vertex1;
        public Vertex vertex2;
        private Edge(Vertex vertex1, Vertex vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
        @Override
        public String toString(){
            return this.vertex1.label + " -> " + this.vertex2.label;
        }
    }

    private List<Vertex> vertices = new ArrayList<>();
    private List<Edge> edgesForDot = new ArrayList<>();
    private List<LinkSource> edgesForPNG = new ArrayList<>();
    private boolean[][] connected;

    private char[] word;
    private int N;

    public DependencyGraph(String word){
        this.word = word.toCharArray();
        this.N = word.length();
        connected = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                connected[i][j] = false;
            }
        }
        createGraphWithAllEdges();
        removeRedundantEdges();
        prepareEdgesForOutput();
    }

    private void createGraphWithAllEdges() {
        createVertices();
        createEdges();
    }
    private void createVertices(){
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int k = map.getOrDefault(word[i],0);
            vertices.add(new Vertex(i, word[i], word[i] + String.valueOf(k)));
            k++;
            map.put(word[i], k);
        }
    }

    private void createEdges(){
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (vertices.get(i).task.isDependent(vertices.get(j).task))
                    connected[i][j] = true;
            }
        }
    }

    private void removeRedundantEdges(){
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = 0; k < N; k++){
                    if (connected[i][j] && connected[i][k] && connected[k][j]) {
                        connected[i][j] = false;
                        break;
                    }
                }
            }
        }
    }

    private void prepareEdgesForOutput(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (connected[j][i]){
                    edgesForDot.add(new Edge(vertices.get(j), vertices.get(i)));

                    Node node = node(vertices.get(j).pngLabel)
                            .link(node(vertices.get(i).pngLabel));
                    edgesForPNG.add(node);
                }
            }
        }
    }

    public void toPNG(String filename) throws IOException {
        Graph g = graph().directed()
                .graphAttr().with(Rank.dir(TOP_TO_BOTTOM))
                .linkAttr().with("class", "link-class")
                .with(
                        edgesForPNG
                );
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(filename));
    }
    public String toDot(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("digraph g{\n");
        for (Edge edge:edgesForDot) {
            stringBuilder.append(edge.vertex1.id + 1);
            stringBuilder.append(" -> ");
            stringBuilder.append(edge.vertex2.id + 1);
            stringBuilder.append("\n");
        }
        for (Vertex vertex:vertices) {
            stringBuilder.append(vertex.id + 1);
            stringBuilder.append("[label=");
            stringBuilder.append(vertex.label);
            stringBuilder.append("]\n");
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

}
