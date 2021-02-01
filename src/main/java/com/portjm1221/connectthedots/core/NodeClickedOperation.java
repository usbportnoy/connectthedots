package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Payload;

public class NodeClickedOperation implements GameOperation{

    private Game game;

    public NodeClickedOperation(Game game) {
        this.game = game;
    }

    @Override
    public Payload execute() {
        return null;
    }
}
