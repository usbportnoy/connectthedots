package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.StateUpdate;
import com.portjm1221.connectthedots.service.GameService;

public class InitializeOperation implements GameOperation{

    private Game game;

    public InitializeOperation(Game game) {
        this.game = game;
    }

    @Override
    public Payload execute() {
        game.setActivePoint(null);
        game.setPlayer(1);
        game.setAdjMatrix(new boolean[game.getVertices()*game.getVertices()][game.getVertices()*game.getVertices()]);

        String heading = String.format("Player %d", game.getPlayer());
        String message = String.format("Awaiting Player %d's Move", game.getPlayer());

        return new Payload("INITIALIZE",
                new StateUpdate(
                        null,
                        GameService.getPlayerNameText(game),
                        GameService.awaitingPlayerTurnText(game)
                )
        );
    }
}
