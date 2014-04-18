package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame;

import java.util.List;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class CompleteView extends JSplitPane {

	GameData gameData;
	VoteData voteData;
	
	public CompleteView(GameSession completedGame){
		gameData = new GameData(completedGame, this);
		voteData = new VoteData(completedGame, this);
		
		
	}

	public List<Requirement> getGameRequirements() {
		// TODO Auto-generated method stub
		return gameData.getGameReqs();
	}
	
}
