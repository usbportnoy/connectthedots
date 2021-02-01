package com.portjm1221.connectthedots.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GameWebController {
    @PostMapping
    public Payload post(@RequestBody Payload body){
        return null;
    }
}
