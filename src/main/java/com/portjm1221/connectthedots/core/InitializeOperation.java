package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.StateUpdate;

public class InitializeOperation implements GameOperation{

    private Game game;

    public InitializeOperation(Game game) {
        this.game = game;
    }

    @Override
    public Payload execute() {
        game = new Game(game.getVertices());
        return new Payload("INITIALIZE", new StateUpdate(null, "Player 1", "Awaiting Player 1's Move"));
    }
}
