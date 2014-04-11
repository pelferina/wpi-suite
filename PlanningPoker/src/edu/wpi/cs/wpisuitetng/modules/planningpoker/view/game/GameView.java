package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.Dimension;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

@SuppressWarnings("serial")
public class GameView extends JSplitPane{

	GameRequirements gameReqs;
	PlayGame playGame;
	
	public GameView (GameSession gameToPlay){
		this.gameReqs = new GameRequirements(gameToPlay, this);
		this.playGame = new PlayGame(gameToPlay, this);
		addImpl(gameReqs, JSplitPane.LEFT, 1);
		Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(playGame, JSplitPane.RIGHT, 2);
	}

	public void sendReqToPlay(Requirement r) {
		playGame.chooseReq(r);
	}

	public void updateReqTables(Requirement r) {
		gameReqs.updateTables(r);
	}
	
}
