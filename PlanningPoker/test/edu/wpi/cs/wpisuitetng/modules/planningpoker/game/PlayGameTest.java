package edu.wpi.cs.wpisuitetng.modules.planningpoker.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.PlayGame;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

public class PlayGameTest {

	private Requirement req1;
	private Requirement req2;
	private Requirement req3;
	/**
	 * Setting up using Network and Iteration
	
	 * @throws Exception */
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		User bob = new User("bob", "bob", "1234", 28);
		admin.setRole(Role.ADMIN);
		req1 = new Requirement(1,"one","Desc");
		req2 = new Requirement(2,"two","Desc");
		req3 = new Requirement(3,"three","Desc");
		RequirementModel.getInstance().addRequirement(req1);
		RequirementModel.getInstance().addRequirement(req2);
		RequirementModel.getInstance().addRequirement(req3);

	}	
	@Test
	public void testPlayDeadlineGame() throws Exception{
		List <Integer> reqList =  new ArrayList<Integer>();
		reqList.add(1);
		//reqList.add(2);
		//reqList.add(3);
		GameSession gs = new GameSession("G","D", 0, 0, Calendar.getInstance().getTime(), reqList);
		gs.setDeckId(-1);
		assertEquals(GameStatus.DRAFT, gs.getGameStatus());
		gs.setGameStatus(GameStatus.ACTIVE);
		GameView gv = new GameView(gs);
		PlayGame pg = new PlayGame(gs, gv);
		assertNotNull(pg);
		assertEquals("G", pg.getGameNameTextField().getText());
		assertEquals("D", pg.getGameDescTextArea().getText());
		assertEquals("one", pg.getReqNameTextField().getText());
		assertEquals("Desc", pg.getReqDescTextArea().getText());
		assertEquals("", pg.getEstimateTextField().getText());
		assertFalse(pg.getVoteButton().isEnabled());
		pg.getEstimateTextField().setText("-5");
		assertFalse(pg.getVoteButton().isEnabled());
		pg.getEstimateTextField().setText("abc");
		assertFalse(pg.getVoteButton().isEnabled());
		pg.getEstimateTextField().setText("6");
		assertTrue(pg.getVoteButton().isEnabled());
		pg.getVoteButton().doClick();
		pg.clear();
		assertTrue(pg.getSubmit().isEnabled());
		//pg.getSubmit().doClick();
	}
	
	@Test
	public void testPlayGameWithoutDeadline() {
		List <Integer> reqList =  new ArrayList<Integer>();
		reqList.add(1);
		//reqList.add(2);
		//reqList.add(3);
		GameSession gs = new GameSession("G","D", 0, 0, null, reqList);
		gs.setDeckId(-1);
		gs.setDeckId(-1);
		GameView gv = new GameView(gs);
		PlayGame pg = new PlayGame(gs, gv);
		assertNotNull(pg);
	}

}
