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
        game.setActivePoint(point);
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

    public boolean isValidEndNode(Game game, Point to) {
        //Is this to adjacent to the active one?
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point activePoint = game.getActivePoint();
                Point adj = new Point(activePoint.getX()+x, activePoint.getY()+y);
                if(to.equals(adj)){
                    //Does the next to already have connections?
                    if(getCountOfPathsConnectedToPoint(game.getAdjMatrix(), to, game.getVertices()) == 0){
                        //No it has no connections and could be a connection to a path.
                        //But is any line intersecting possible path?
                        //Vertical and horizontal lines cant be intersected because of the layout of the dot
                        //If it only contains a change to only x, or y but not both its a vertical line
                        int dX = activePoint.getX() - to.getX();
                        int dY = activePoint.getY() - to.getY();
                        if(dX != 0 && dY == 0){
                            return true;
                        }else if(dX == 0 && dY != 0){
                            return true;
                        }else {
                            if(dX > 0 && dY < 0){
                                //DownLeft
                                //From -x
                                //To +x
                                Point toDiag = new Point(activePoint.getX()-1, activePoint.getY());
                                Point fromDiag = new Point(to.getX()+1, to.getY());
                                if(isPointInsideBoardBounds(game.getVertices(), fromDiag) && isPointInsideBoardBounds(game.getVertices(), toDiag)){
                                    return !pathExists(game, fromDiag, toDiag);
                                }else {
                                    return true;
                                }
                            }else if(dX < 0 && dY > 0){
                                //UpRight
                                Point fromDiag = new Point(activePoint.getX()+1, activePoint.getY());
                                Point toDiag = new Point(to.getX()-1, to.getY());
                                if(isPointInsideBoardBounds(game.getVertices(), fromDiag) && isPointInsideBoardBounds(game.getVertices(), toDiag)){
                                    return !pathExists(game, fromDiag, toDiag);
                                }else {
                                    return true;
                                }
                            }else if(dX < 0 && dY < 0){
                                //DownRight
                                //From +x
                                Point fromDiag = new Point(activePoint.getX()+1, activePoint.getY());
                                Point toDiag = new Point(to.getX()-1, to.getY());
                                if(isPointInsideBoardBounds(game.getVertices(), fromDiag) && isPointInsideBoardBounds(game.getVertices(), toDiag)){
                                    return !pathExists(game, fromDiag, toDiag);
                                }else {
                                    return true;
                                }
                                //To -x
                            }else if(dX > 0 && dY > 0){
                                //UpLeft
                                Point fromDiag = new Point(to.getX(), to.getY()+1);
                                Point toDiag =  new Point(activePoint.getX(), activePoint.getY()-1);
                                if(isPointInsideBoardBounds(game.getVertices(), fromDiag) && isPointInsideBoardBounds(game.getVertices(), toDiag)){
                                    return !pathExists(game, fromDiag, toDiag);
                                }else {
                                    return true;
                                }
                            }
                        }
                    }
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

    private boolean isPointInsideBoardBounds(int vertices, Point point) {
        int x = point.getX();
        int y = point.getY();
        return (x >= 0 && y >= 0) && (x < vertices && y < vertices);
    }

    private boolean isCleanBoard(boolean[][] adjMatrix) {
        for (boolean[] matrix : adjMatrix) {
            for (boolean b : matrix) {
                if(b) return false;
            }
        }

        return true;
    }

    private boolean pathExists(Game game, Point from, Point to){
        int fromPoint = getIndexFromPoint(from, game.getVertices());
        int toPoint = getIndexFromPoint(to, game.getVertices());
        return game.getAdjMatrix()[toPoint][fromPoint] || game.getAdjMatrix()[fromPoint][toPoint];
    }

}
