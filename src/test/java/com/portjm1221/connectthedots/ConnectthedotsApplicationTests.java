package com.portjm1221.connectthedots;

import com.portjm1221.connectthedots.core.Game;
import com.portjm1221.connectthedots.core.GameExecutor;
import com.portjm1221.connectthedots.core.NodeClickedOperation;
import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.Point;
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

	@Test
	void firstMove() {
		Game game = new Game(4);
		GameExecutor gameExecutor = new GameExecutor(game);
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		assertThat(payload.getMsg()).isEqualTo("VALID_START_NODE");
	}
}
