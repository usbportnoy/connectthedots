package com.portjm1221.connectthedots.service;

import com.portjm1221.connectthedots.core.Game;
import com.portjm1221.connectthedots.core.models.Point;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    public static String SelectSecondNodeText = "Select a second node to complete the line.";
    public static String NotValidStartingPosition = "Not a valid starting position.";
    public static String InvalidMoveText = "Invalid Move";
    public static String GameOver = "Game Over";

    public static String getPlayerNameText(int player){
        return String.format("Player %d", player);
    }

    public static String awaitingPlayerTurnText(int player){
        return String.format("Awaiting Player %d's Move", player);
    }

    public static String playerWins(int player){
        return String.format("Player %d Wins!", player);
    }

    /**
     *
     * @param point     Point requesting index for
     * @param vertices  Number of vertices
     * @return          index of node
     */
    public static int getIndexFromPoint(Point point, int vertices) {
        return (point.getY() * vertices) + point.getX();
    }

    /**
     * Moves player counter to the next player. Loops back to start
     * @param game
     */
    public void rotatePlayer(Game game){
        if(game.getPlayer()+1 > game.getMaxPlayers()){
            game.setPlayer(1);
        }else{
            game.setPlayer(game.getPlayer()+1);
        }
    }

    /**
     * Creates a path between points A and B
     * @param adjMatrix Adjacent Matrix of paths
     * @param fromPoint Point A
     * @param toPoint   Point B
     * @param vertices  Number of vertices
     */
    public void setPath(boolean[][] adjMatrix, Point fromPoint, Point toPoint, int vertices) {
        int from = getIndexFromPoint(fromPoint, vertices);
        int to = getIndexFromPoint(toPoint, vertices);
        adjMatrix[from][to] = true;
        adjMatrix[to][from] = true;
    }


    public void clearActivePoint(Game game){
        game.setActivePoint(null);
    }

    /**
     * Sets the active point for the Game. This is the first half of a move
     * @param game
     * @param point Point to be active
     */
    public void setActivePoint(Game game, Point point){
        game.setActivePoint(point);
    }

    /**
     * Determines if this is the end of a path. An end path can only have a single connected point
     *
     * @param adjMatrix
     * @param point End node of a path
     * @param vertices
     * @return
     */
    public boolean isEndOfPath(boolean[][] adjMatrix, Point point, int vertices) {
        return getCountOfPathsConnectedToPoint(adjMatrix, point, vertices) == 1;
    }

    public int getCountOfPathsConnectedToPoint(boolean[][] adjMatrix, Point point, int vertices) {
        int count = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point adj = new Point(point.getX()+x, point.getY()+y);
                if(isPointInsideBoardBounds(vertices, adj) && !point.equals(adj)){
                    if(pathExists(adj, point, adjMatrix, vertices)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Validates if the node is allowed to be an end node. A node that can be an End node can not have a path already connected
     *
     * @param to Destination node
     * @param activePoint Origin node
     * @param adjMatrix
     * @param vertices
     * @return
     */
    public boolean isValidEndNode(Point to, Point activePoint, boolean[][] adjMatrix, int vertices) {
        //Is this node adjacent to the active one?
        if(isNodeAdjacent(activePoint, to)){
            //Does the next to already have connections?
            if (getCountOfPathsConnectedToPoint(adjMatrix, to, vertices) == 0) {
                //No it has no connections and could be a connection to a path.
                //But is any line intersecting possible path?
                //Vertical and horizontal lines cant be intersected because of the layout of the dot
                //If it only contains a change to only x, or y but not both its a vertical line
                int dX = activePoint.getX() - to.getX();
                int dY = activePoint.getY() - to.getY();
                if (dX != 0 && dY == 0) {
                    return true;
                } else if (dX == 0 && dY != 0) {
                    return true;
                } else {
                    if(dX > 0 && dY < 0){
                        //DownLeft
                        return shiftPoint(activePoint.getX()-1, activePoint.getY(), to.getX()+1, to.getY(), vertices, adjMatrix);
                    }else if(dX < 0 && dY > 0){
                        //UpRight
                        return shiftPoint(activePoint.getX()+1, activePoint.getY(), to.getX()-1, to.getY(), vertices, adjMatrix);
                    }else if(dX < 0 && dY < 0){
                        //DownRight
                        return shiftPoint(activePoint.getX()+1, activePoint.getY(), to.getX()-1, to.getY(), vertices, adjMatrix);
                    }else if(dX > 0 && dY > 0){
                        //UpLeft
                        return shiftPoint(to.getX(), to.getY() + 1, activePoint.getX(), activePoint.getY() - 1, vertices, adjMatrix);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Needs to be the end of a line, or start from a clean board
     * @param point
     * @param adjMatrix
     * @param vertices
     * @return
     */
    public boolean isValidStartNode(Point point, boolean[][] adjMatrix, int vertices) {
        //Is this the start of the game?
        if(isCleanBoard(adjMatrix)){
            return true;
        }else {
            //Is it the end of a path? -- The end of a path will only have a single path connecting
            return isEndOfPath(adjMatrix, point, vertices);
        }
    }

    /**
     * Check if a game has no moves left
     * @param adjMatrix
     * @param vertices
     * @return
     */
    public boolean isGameOver(boolean[][] adjMatrix, int vertices) {
        List<Point> startPoints = getStartPoints(adjMatrix, vertices);
        boolean hasMoves = false;
        for (Point startPoint : startPoints) {
            if(isValidEndNode(adjMatrix, startPoint, vertices)){
                hasMoves = true;
                break;
            }
        }
        return !hasMoves;
    }

    /**
     * Finds all start points on the board
     * @param adjMatrix
     * @param vertices
     * @return
     */
    public List<Point> getStartPoints(boolean[][] adjMatrix, int vertices){
        List<Point> points = new ArrayList<>();
        for (int x = 0; x < vertices; x++) {
            for (int y = 0; y < vertices; y++) {
                Point point = new Point(x, y);
                if(isValidStartNode(point, adjMatrix, vertices)){
                    points.add(point);
                }
            }
        }
        return points;
    }

    private boolean isValidEndNode(boolean[][] adjMatrix, Point point, int vertices) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point adj = new Point(point.getX()+x, point.getY()+y);
                if(isPointInsideBoardBounds(vertices, adj) && !point.equals(adj)){
                    if(isValidEndNode(adj, point, adjMatrix, vertices)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isNodeAdjacent(Point origin, Point other){
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point adj = new Point(origin.getX()+x, origin.getY()+y);
                if(other.equals(adj)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean shiftPoint(int fromX, int fromY, int toX, int toY, int vertices, boolean[][] adjMatrix) {
        Point fromDiag = new Point(fromX, fromY);
        Point toDiag = new Point(toX, toY);
        if (isPointInsideBoardBounds(vertices, fromDiag) && isPointInsideBoardBounds(vertices, toDiag)) {
            return !pathExists(fromDiag, toDiag, adjMatrix, vertices);
        } else {
            return true;
        }
    }

    private boolean isPointInsideBoardBounds(int vertices, Point point) {
        int x = point.getX();
        int y = point.getY();
        return (x >= 0 && y >= 0) && (x < vertices && y < vertices);
    }

    private boolean isCleanBoard(boolean[][] adjMatrix) {
        for (boolean[] matrix : adjMatrix) {
            for (boolean path : matrix) {
                if(path) return false;
            }
        }

        return true;
    }

    private boolean pathExists(Point from, Point to, boolean[][] adjMatrix, int vertices){
        int fromPoint = getIndexFromPoint(from, vertices);
        int toPoint = getIndexFromPoint(to, vertices);
        if(fromPoint < 0 || toPoint < 0){
            return false;
        }
        return adjMatrix[toPoint][fromPoint];
    }

}
