package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Line;
import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.Point;
import com.portjm1221.connectthedots.core.models.StateUpdate;
import com.portjm1221.connectthedots.service.GameService;

public class NodeClickedOperation implements GameOperation{

    private Game game;
    private Point point;
    private GameService gameService;

    public NodeClickedOperation(Game game, Point point) {
        this.game = game;
        this.point = point;
        this.gameService = new GameService();
    }

    @Override
    public Payload execute() {
        //Are we in the middle of a players move?
        if(game.getActivePoint() == null){
            //No, is node valid start node?
            if(gameService.isValidStartNode(point, game.getAdjMatrix(), game.getVertices())){
                gameService.setActivePoint(game, point);
                return getPayload("VALID_START_NODE", GameService.SelectSecondNodeText, null, GameService.getPlayerNameText(game.getPlayer()));
            }else {
                return getInvalidStartNodePayload();
            }
        }else{
            //Yes, is node valid end node?
            if(gameService.isValidEndNode(point, game.getActivePoint(), game.getAdjMatrix(), game.getVertices())){
                //Does this win the game?
                if(!gameService.endOfLine(game.getAdjMatrix(), point, game.getVertices())){
                    gameService.rotatePlayer(game);
                    Payload payload = getValidEndNodePayload();
                    gameService.setPath(game.getAdjMatrix(), game.getActivePoint(), point, game.getVertices());
                    gameService.clearActivePoint(game);
                    return payload;
                }else {
                    return getPayload("GAME_OVER", GameService.playerWins(game.getPlayer()), getNewLine(), GameService.GameOver);
                }
            }else {
                //On failure to pick a correct node, it resets on the frontend active node status
                gameService.clearActivePoint(game);
                return getPayload("INVALID_END_NODE", GameService.InvalidMoveText, null, GameService.getPlayerNameText(game.getPlayer()));
            }
        }
    }

    private Payload getPayload(String msg, String message, Line newLine, String heading) {
        return new Payload(msg, new StateUpdate(newLine, heading, message));
    }

    private Payload getInvalidStartNodePayload() {
        return getPayload("INVALID_START_NODE", GameService.NotValidStartingPosition, null, GameService.getPlayerNameText(game.getPlayer()));
    }

    private Payload getValidEndNodePayload() {
        return getPayload("VALID_END_NODE", null, getNewLine(), GameService.getPlayerNameText(game.getPlayer()));
    }

    private Line getNewLine() {
        return new Line(
                new Point(game.getActivePoint().getX(), game.getActivePoint().getY()),
                new Point(point.getX(), point.getY())
        );
    }


}
