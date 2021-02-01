package com.portjm1221.connectthedots.web;

import com.portjm1221.connectthedots.core.ErrorOperation;
import com.portjm1221.connectthedots.core.GameExecutor;
import com.portjm1221.connectthedots.core.InitializeOperation;
import com.portjm1221.connectthedots.core.NodeClickedOperation;
import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.Point;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GameWebController {
    private GameExecutor gameExecutor;

    public GameWebController(GameExecutor gameExecutor) {
        this.gameExecutor = gameExecutor;
    }

    @GetMapping("/initialize")
    public Payload initialize(){
        return gameExecutor.executeOperation(new InitializeOperation(gameExecutor.getGame()));
    }

    @PostMapping("/node-clicked")
    public Payload nodeClicked(@RequestBody Point body){
        return gameExecutor.executeOperation(new NodeClickedOperation(gameExecutor.getGame(), body));
    }

    @PostMapping("/error")
    public Payload error(@RequestBody ErrorModel body){
        return gameExecutor.executeOperation(new ErrorOperation(body.getError()));
    }

}
