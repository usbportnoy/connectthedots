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
                return new Payload.PayloadBuilder()
                        .messageIs(Msg.ValidStartNode)
                        .withPlayerHeading(game.getPlayer())
                        .say(GameService.SelectSecondNodeText)
                        .build();

            }else {
                return new Payload.PayloadBuilder()
                        .messageIs(Msg.InvalidStartNode)
                        .withPlayerHeading(game.getPlayer())
                        .say(getInvalidStartNodeText())
                        .build();
            }
        }else{
            //Yes, is node valid end node?
            if(gameService.isValidEndNode(point, game.getActivePoint(), game.getAdjMatrix(), game.getVertices())){
                gameService.setPath(game.getAdjMatrix(), game.getActivePoint(), point, game.getVertices());
                gameService.rotatePlayer(game);
                //Does this win the game?
                if(!gameService.isGameOver(game.getAdjMatrix(), game.getVertices())){
                    Payload payload = new Payload.PayloadBuilder()
                            .messageIs(Msg.ValidEndNode)
                            .withPlayerHeading(game.getPlayer())
                            .add(getNewLine(game, point))
                            .build();

                    gameService.clearActivePoint(game);
                    return payload;
                }else {
                    logger.debug("Game Over");
                    return new Payload.PayloadBuilder()
                            .messageIs(Msg.GameOver)
                            .withHeading(GameService.GameOverText)
                            .say(GameService.playerWins(game.getPlayer()))
                            .add(getNewLine(game, point))
                            .build();
                }
            }else {
                //On failure to pick a correct node, it resets on the frontend active node status.
                gameService.clearActivePoint(game);

                return new Payload.PayloadBuilder()
                        .messageIs(Msg.InvalidEndNode)
                        .withPlayerHeading(game.getPlayer())
                        .say(GameService.InvalidMoveText)
                        .build();
            }
        }
    }


    private String getInvalidStartNodeText() {
        if (gameService.isCleanBoard(game.getAdjMatrix())) {
            return GameService.NotValidStartingPositionText;
        }
        return GameService.StartOnEndOfAPath;
    }

    private Line getNewLine(Game game, Point point) {
        return new Line(
                new Point(game.getActivePoint().getX(), this.game.getActivePoint().getY()),
                new Point(point.getX(), this.point.getY())
        );
    }


}
