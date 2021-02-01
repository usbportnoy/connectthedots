package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.Point;
import com.portjm1221.connectthedots.core.models.StateUpdate;

public class NodeClickedOperation implements GameOperation{

    private Game game;
    private Point point;

    public NodeClickedOperation(Game game, Point point) {
        this.game = game;
        this.point = point;
    }

    @Override
    public Payload execute() {
        //Are we in the middle of a players move?
        if(!game.isNodeActive()){
            //No, is node valid start node?
            if(isValidStartNode(game, point)){
                game.setNodeActive(true);
                return new Payload("VALID_START_NODE", new StateUpdate(null, "Player 2", "Select a second node to complete the line."));
            }else {
                return new Payload("INVALID_START_NODE", new StateUpdate(null, "Player 2", "Select a second node to complete the line."));
            }
        }else{
            //Yes, is node valid end node?
            //Does this win the game?
        }
        return null;
    }

    private boolean isValidStartNode(Game game, Point point) {
        //Is this the start of the game?
        if(isCleanBoard(game.getAdjMatrix())){
            return true;
        }else {
            //Is it the end of a path? -- The end of a path will only have a single path connecting
            return isEndOfPath(game.getAdjMatrix(), point, game.getVertices());
        }
    }

    private boolean isEndOfPath(boolean[][] adjMatrix, Point point, int vertices) {
        int count = getCountOfPathsToPoint(adjMatrix, point, vertices);

        return count == 1;
    }

    private int getCountOfPathsToPoint(boolean[][] adjMatrix, Point point, int vertices) {
        int count = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Point adj = new Point(point.getX()+x, point.getY()+y);
                if((adj.getX() >= 0 && adj.getY() >= 0) && (adj.getX() + adj.getY() < vertices)){
                    int from = point.getX() + point.getY();
                    int to = adj.getX() + adj.getY();
                    if(adjMatrix[from][to]){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isCleanBoard(boolean[][] adjMatrix) {
        if(adjMatrix == null){
            //todo throw error
        }

        for (boolean[] matrix : adjMatrix) {
            for (boolean b : matrix) {
                if(b) return false;
            }
        }

        return true;
    }
}
