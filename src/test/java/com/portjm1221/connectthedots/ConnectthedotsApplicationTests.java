package com.portjm1221.connectthedots;

import com.portjm1221.connectthedots.core.Game;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConnectthedotsApplicationTests {
	@Test
	void printsGame() {
		Game game = new Game(4);
		assertThat(game.toString()).isNotNull();
	}
}
