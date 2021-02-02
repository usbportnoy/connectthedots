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

        return new Payload("INITIALIZE",
                new StateUpdate(
                        null,
                        GameService.getPlayerNameText(game.getPlayer()),
                        GameService.awaitingPlayerTurnText(game.getPlayer())
                )
        );
    }
}
