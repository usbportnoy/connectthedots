package com.portjm1221.connectthedots.core;

public class Game {

    private boolean[][] adjMatrix;
    private int vertices;

    public Game(int vertices) {
        this.vertices = vertices;
        adjMatrix = new boolean[vertices][vertices];
    }

    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    public void setAdjMatrix(boolean[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int row = 0; row < vertices; row++) {
            s.append(row).append(": ");
            for (boolean col : adjMatrix[row]) {
                s.append(col ? 1 : 0).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
