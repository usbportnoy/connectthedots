package com.portjm1221.connectthedots.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GameWebController {
    @GetMapping("/initialize")
    public Payload initialize(@RequestBody Payload body){
        return null;
    }

    @PostMapping("/node-clicked")
    public Payload nodeClicked(@RequestBody Payload body){
        return null;
    }

    @PostMapping("/error")
    public Payload error(@RequestBody Payload body){
        return null;
    }

}
