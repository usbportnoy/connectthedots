package com.portjm1221.connectthedots;

import com.portjm1221.connectthedots.core.Game;
import com.portjm1221.connectthedots.core.GameExecutor;
import com.portjm1221.connectthedots.core.NodeClickedOperation;
import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.Point;
import com.portjm1221.connectthedots.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConnectthedotsApplicationTests {
	@Test
	void printsGame() {
		Game game = new Game(4, 2);
		assertThat(game.toString()).isNotNull();
	}

	@Test
	void validStart() {
		Game game = new Game(4, 2);
		GameExecutor gameExecutor = new GameExecutor(game);
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		assertThat(payload.getMsg()).isEqualTo("VALID_START_NODE");
	}

	@Test
	void validEndMove(){
		Game game = new Game(4, 2);
		GameExecutor gameExecutor = new GameExecutor(game);
		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 0)));
		assertThat(payload.getMsg()).isEqualTo("VALID_END_NODE");
	}

}
