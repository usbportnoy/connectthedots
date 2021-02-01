package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Payload;

import java.util.ArrayList;
import java.util.List;

public class GameExecutor {
    private List<GameOperation> operations;
    private Game game;

    public GameExecutor(Game game) {
        this.game = game;
        this.operations = new ArrayList<>();
    }

    public synchronized Payload executeOperation(GameOperation gameOperation){
        operations.add(gameOperation);
        return gameOperation.execute();
    }

    public List<GameOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<GameOperation> operations) {
        this.operations = operations;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
