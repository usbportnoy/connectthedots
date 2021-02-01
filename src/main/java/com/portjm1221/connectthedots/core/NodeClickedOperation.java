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
            if(gameService.isValidStartNode(game, point)){
                gameService.setActivePoint(game, point);
                gameService.rotatePlayer(game);
                return getInvalidPayload("VALID_START_NODE", GameService.SelectSecondNodeText);
            }else {
                return getInvalidStartNodePayload();
            }
        }else{
            //Yes, is node valid end node?
            if(gameService.isValidEndNode(game, point)){
                gameService.rotatePlayer(game);
                Payload payload = getValidEndNodePayload();
                gameService.setPath(game.getAdjMatrix(), game.getActivePoint(), point, game.getVertices());
                gameService.clearActivePoint(game);
                return payload;
                //Does this win the game?

            }else {
                //On failure to pick a correct node, it resets on the frontend active node status
                gameService.clearActivePoint(game);
                return getInvalidPayload("INVALID_END_NODE", GameService.InvalidMoveText);
            }
        }
    }

    private Payload getInvalidPayload(String invalidMsg, String invalidMoveText) {
        return new Payload(
                invalidMsg,
                new StateUpdate(
                        null,
                        GameService.getPlayerNameText(game),
                        invalidMoveText
                )
        );
    }

    private Payload getInvalidStartNodePayload() {
        return getInvalidPayload("INVALID_START_NODE", GameService.NotValidStartingPosition);
    }

    private Payload getValidEndNodePayload() {
        return new Payload(
                "VALID_END_NODE",
                new StateUpdate(
                        new Line(
                                new Point(game.getActivePoint().getX(), game.getActivePoint().getY()),
                                new Point(point.getX(), point.getY())
                        ),
                        GameService.getPlayerNameText(game),
                        null
                )
        );
    }





}
