package com.portjm1221.connectthedots;

import com.portjm1221.connectthedots.core.Game;
import com.portjm1221.connectthedots.core.GameExecutor;
import com.portjm1221.connectthedots.core.NodeClickedOperation;
import com.portjm1221.connectthedots.core.models.Payload;
import com.portjm1221.connectthedots.core.models.Point;
import com.portjm1221.connectthedots.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConnectthedotsApplicationTests {
	@Test
	void validStart() {
		Game game = new Game(4, 2);
		GameExecutor gameExecutor = new GameExecutor(game);
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		assertThat("VALID_START_NODE").isEqualTo(payload.getMsg());

	}

	@Test
	void validEndMove(){
		Game game = new Game(4, 2);
		GameExecutor gameExecutor = new GameExecutor(game);
		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 0)));
		assertThat("VALID_END_NODE").isEqualTo(payload.getMsg());
	}

	@Test
	void listPoints(){
		Game game = new Game(4, 2);
		GameExecutor gameExecutor = new GameExecutor(game);
		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 0)));
		GameService gameService = new GameService();
		List<Point> startPoints = gameService.getStartPoints(game.getAdjMatrix(), game.getVertices());
		assertThat(2).isEqualTo(startPoints.size());
	}

	@Test
	void gameOver(){
		Game game = new Game(2, 2);
		GameExecutor gameExecutor = new GameExecutor(game);
		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 0)));
		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 0)));

		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 0)));
		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 1)));

		gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(1, 1)));
		Payload payload = gameExecutor.executeOperation(new NodeClickedOperation(game, new Point(0, 1)));
		assertThat("GAME_OVER").isEqualTo(payload.getMsg());
	}

	@Test
	void index(){
		int zero = GameService.getIndexFromPoint(new Point(0, 0), 4);
		assertThat(0).isEqualTo(zero);

		int three = GameService.getIndexFromPoint(new Point(3, 0), 4);
		assertThat(3).isEqualTo(three);

		int eight = GameService.getIndexFromPoint(new Point(0, 2), 4);
		assertThat(8).isEqualTo(eight);

		int fifteen = GameService.getIndexFromPoint(new Point(3, 3), 4);
		assertThat(15).isEqualTo(fifteen);
	}
}
