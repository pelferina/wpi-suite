package edu.wpi.cs.wpisuitetng.modules.planningpoker.completegame;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.CompleteView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.VoteData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;


public class TestVoteData {
	
	
	@Test
	public void testVote(){	
		Date testdate = new Date(2014, 6 , 27, 15, 06);
		int req1 = 0;
		List<Integer> reqs = new ArrayList<Integer>();
		reqs.add(req1);
		GameSession testGame = new GameSession("Test Game", "Test Description", 0, 0, new Date(), reqs);
//		CompleteView cv = new CompleteView(testGame);
//		VoteData testVote = new VoteData(testGame,cv);
//		boolean result = testVote.isEnabled();
//		assertEquals (true, result);
	}
	
}