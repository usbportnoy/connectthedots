package com.portjm1221.connectthedots.config;

import com.portjm1221.connectthedots.core.Game;
import com.portjm1221.connectthedots.core.GameExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameExecutorConfig {

    @Bean
    public GameExecutor gameExecutor(){
        return new GameExecutor(new Game(4));
    }

}
