package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Msg;
import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.StateUpdate;
import com.portjm1221.connectthedots.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializeOperation implements GameOperation{
    Logger logger = LoggerFactory.getLogger(InitializeOperation.class);

    private Game game;

    public InitializeOperation(Game game) {
        this.game = game;
    }

    @Override
    public Payload execute() {
        logger.info("Init game");
        new GameService().init(game);

        return new Payload(Msg.Initialize.toString(),
                new StateUpdate(
                        null,
                        GameService.getPlayerNameText(game.getPlayer()),
                        GameService.awaitingPlayerTurnText(game.getPlayer())
                )
        );
    }
}
