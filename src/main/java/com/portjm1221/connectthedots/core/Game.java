package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Point;

public class Game {

    private boolean[][] adjMatrix;
    private int vertices;
    private int maxPlayers;
    private Point activePoint;
    private int player;

    public Game(int vertices, int maxPlayers) {
        this.vertices = vertices;
        this.maxPlayers = maxPlayers;
        player=1;
        adjMatrix = new boolean[vertices*vertices][vertices*vertices];
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

    public Point getActivePoint() {
        return activePoint;
    }

    public void setActivePoint(Point activePoint) {
        this.activePoint = activePoint;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
