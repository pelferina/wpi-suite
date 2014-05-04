/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.PlayDeckGame;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

public class testPlayDeckGame {
	private Requirement req1;
	private Requirement req2;
	private Requirement req3;
	/**
	 * Setting up using Network and Iteration
	
	 * @throws Exception */
	
	@Before
	public void setUp() throws Exception {
		final User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		req1 = new Requirement(1,"one","Desc");
		req2 = new Requirement(2,"two","Desc");
		req3 = new Requirement(3,"three","Desc");
		RequirementModel.getInstance().addRequirement(req1);
		RequirementModel.getInstance().addRequirement(req2);
		RequirementModel.getInstance().addRequirement(req3);

	}
	@Test
	public void testAPlayDeckGame() throws Exception{
		List <Integer> reqList =  new ArrayList<Integer>();
		reqList.add(1);
		//reqList.add(2);
		//reqList.add(3);
		GameSession gs = new GameSession("G","D", 0, 0, Calendar.getInstance().getTime(), reqList);
		gs.setDeckId(0);
		GameView gv = new GameView(gs);
		PlayDeckGame pdg = new PlayDeckGame(gs, gv);
		assertNotNull(pdg);
		assertEquals("G", pdg.getGameNameTextField().getText());
		assertEquals("D", pdg.getGameDescTextArea().getText());
		assertEquals(0, pdg.getDeckId());
		assertEquals("one", pdg.getReqNameTextField().getText());
		assertEquals("Desc", pdg.getReqDescTextArea().getText());
		List<GameCard> cardButtons = pdg.getCardButtons();
		assertEquals(8, cardButtons.size());
		assertFalse(pdg.getVoteButton().isEnabled());
		cardButtons.get(0).doClick();
		assertEquals("0", pdg.getVotesSoFarLabel().getText());
		cardButtons.get(1).doClick();
		cardButtons.get(2).doClick();
		assertEquals("2", pdg.getVotesSoFarLabel().getText());
		assertTrue(pdg.getVoteButton().isEnabled());
		pdg.getVoteButton().doClick();
//		assertEquals("two", pdg.getReqNameTextField().getText());
//		assertEquals("", pdg.getVotesSoFarLabel().getText());
		
	}

}
