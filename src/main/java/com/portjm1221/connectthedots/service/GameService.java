package com.portjm1221.connectthedots.service;

import com.portjm1221.connectthedots.core.Game;
import com.portjm1221.connectthedots.core.models.Point;

public class GameService {
    public static String SelectSecondNodeText = "Select a second node to complete the line.";
    public static String NotValidStartingPosition = "Not a valid starting position.";
    public static String InvalidMoveText = "Invalid Move";

    public static String getPlayerNameText(Game game){
        return String.format("Player %d", game.getPlayer());
    }

    public static String awaitingPlayerTurnText(Game game){
        return String.format("Awaiting Player %d's Move", game.getPlayer());
    }

    public static int getIndexFromPoint(Point point, int vertices) {
        return (point.getY() * vertices) + point.getX();
    }

    public void rotatePlayer(Game game){
        if(game.getPlayer()+1 > game.getMaxPlayers()){
            game.setPlayer(0);
        }else{
            game.setPlayer(game.getPlayer()+1);
        }
    }

    public void setPath(boolean[][] adjMatrix, Point fromPoint, Point toPoint, int vertices) {
        int from = getIndexFromPoint(fromPoint, vertices);
        int to = getIndexFromPoint(toPoint, vertices);
        adjMatrix[from][to] = true;
    }

    public void clearActivePoint(Game game){
        game.setActivePoint(null);
    }

    public void setActivePoint(Game game, Point point){
        game.setActivePoint(null);
    }

    public boolean isEndOfPath(boolean[][] adjMatrix, Point point, int vertices) {
        return getCountOfPathsConnectedToPoint(adjMatrix, point, vertices) == 1;
    }

    public int getCountOfPathsConnectedToPoint(boolean[][] adjMatrix, Point point, int vertices) {
        int count = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point adj = new Point(point.getX()+x, point.getY()+y);
                if(isPointInsideBoardBounds(vertices, adj) && !point.equals(adj)){
                    int from = GameService.getIndexFromPoint(adj, vertices);
                    int to = GameService.getIndexFromPoint(point, vertices);

                    if(adjMatrix[from][to]){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean isValidEndNode(Game game, Point point) {
        //Is this point adjacent to the active one?
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point adj = new Point(game.getActivePoint().getX()+x, game.getActivePoint().getY()+y);
                if(point.equals(adj)){
                    //Does the next point already have connections?
                    return getCountOfPathsConnectedToPoint(game.getAdjMatrix(), point, game.getVertices()) == 0;
                }
            }
        }

        return false;
    }

    public boolean isValidStartNode(Game game, Point point) {
        //Is this the start of the game?
        if(isCleanBoard(game.getAdjMatrix())){
            return true;
        }else {
            //Is it the end of a path? -- The end of a path will only have a single path connecting
            return isEndOfPath(game.getAdjMatrix(), point, game.getVertices());
        }
    }

    private boolean isPointInsideBoardBounds(int vertices, Point adj) {
        return (adj.getX() >= 0 && adj.getY() >= 0) && (adj.getX() + adj.getY() < vertices);
    }

    private boolean isCleanBoard(boolean[][] adjMatrix) {
        for (boolean[] matrix : adjMatrix) {
            for (boolean b : matrix) {
                if(b) return false;
            }
        }

        return true;
    }

}
