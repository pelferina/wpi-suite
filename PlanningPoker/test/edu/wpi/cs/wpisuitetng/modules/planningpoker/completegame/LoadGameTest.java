/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Cosmic Latte
 ******************************************************************************/

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
		final ArrayList<Integer> reqArray = new ArrayList<Integer>();
		reqArray.add(0);
		final GameSession testGame = new GameSession("Test Game", "Test Description", 0, 0, new Date(), reqArray);
		final CompleteView cv = new CompleteView(testGame);
		final VoteData vd = cv.getVoteData();
		final GameData gd = cv.getGameData();
		assertTrue(gd.getGameNameTextBox().getText().equals("Test Game"));
		assertTrue(gd.getDescriptionTextArea().getText().equals("Test Description"));
	}
}
