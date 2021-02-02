package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.*;
import com.portjm1221.connectthedots.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeClickedOperation implements GameOperation{
    Logger logger = LoggerFactory.getLogger(NodeClickedOperation.class);

    private Game game;
    private Point point;
    private GameService gameService;

    public NodeClickedOperation(Game game, Point point) {
        this.game = game;
        this.point = point;
        this.gameService = new GameService();
    }

    /**
     * Main Game Logic entry point.
     * Determines current state of the game, and updates the board according to the point of the click.
     * Returns Payload object to send back to the client.
     * @return
     */
    @Override
    public Payload execute() {
        logger.info("Click " + point.toString());
        //Are we in the middle of a players move?
        if(game.getActivePoint() == null){
            //No, is node valid start node?
            if(gameService.isValidStartNode(point, game.getAdjMatrix(), game.getVertices())){
                gameService.setActivePoint(game, point);
                return getPayload(
                        Msg.ValidStartNode.toString(),
                        GameService.SelectSecondNodeText,
                        null,
                        GameService.getPlayerNameText(game.getPlayer())
                );
            }else {
                return getInvalidStartNodePayload();
            }
        }else{
            //Yes, is node valid end node?
            if(gameService.isValidEndNode(point, game.getActivePoint(), game.getAdjMatrix(), game.getVertices())){
                gameService.setPath(game.getAdjMatrix(), game.getActivePoint(), point, game.getVertices());
                gameService.rotatePlayer(game);
                //Does this win the game?
                if(!gameService.isGameOver(game.getAdjMatrix(), game.getVertices())){
                    Payload payload = getValidEndNodePayload();
                    gameService.clearActivePoint(game);
                    return payload;
                }else {
                    logger.debug("Game Over");
                    return getPayload(
                            Msg.GameOver.toString(),
                            GameService.playerWins(game.getPlayer()), getNewLine(),
                            GameService.GameOverText
                    );
                }
            }else {
                //On failure to pick a correct node, it resets on the frontend active node status.
                gameService.clearActivePoint(game);
                return getPayload(
                        Msg.InvalidEndNode.toString(),
                        GameService.InvalidMoveText,
                        null,
                        GameService.getPlayerNameText(game.getPlayer())
                );
            }
        }
    }

    private Payload getPayload(String msg, String message, Line newLine, String heading) {
        return new Payload(msg, new StateUpdate(newLine, heading, message));
    }

    private Payload getInvalidStartNodePayload() {
        return getPayload(
                Msg.InvalidStartNode.toString(),
                getInvalidStartNodeText(),
                null,
                GameService.getPlayerNameText(game.getPlayer())
        );
    }

    private String getInvalidStartNodeText() {
        if (gameService.isCleanBoard(game.getAdjMatrix())) {
            return GameService.NotValidStartingPositionText;
        }
        return GameService.StartOnEndOfAPath;
    }

    private Payload getValidEndNodePayload() {
        return getPayload(
                Msg.InvalidEndNode.toString(),
                null,
                getNewLine(),
                GameService.getPlayerNameText(game.getPlayer())
        );
    }

    private Line getNewLine() {
        return new Line(
                new Point(game.getActivePoint().getX(), game.getActivePoint().getY()),
                new Point(point.getX(), point.getY())
        );
    }


}
