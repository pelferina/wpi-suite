package edu.wpi.cs.wpisuitetng.modules.planningpoker.completegame;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.CompleteView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.GameData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.VoteData;
import static org.junit.Assert.*;

public class LoadGameTest {

	@Test
	public void testLoadCompleteGame(){
		ArrayList<Integer> reqArray = new ArrayList<Integer>();
		reqArray.add(0);
		GameSession testGame = new GameSession("Test Game", "Test Description", 0, 0, new Date(), reqArray);
		CompleteView cv = new CompleteView(testGame);
		VoteData vd = cv.getVoteData();
		GameData gd = cv.getGameData();
		assertTrue(gd.getGameNameTextBox().getText().equals("Test Game"));
		assertTrue(gd.getDescriptionTextArea().getText().equals("Test Description"));
	}
}
