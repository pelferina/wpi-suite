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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;


	
	public class testTabName {
		GameSession game = new GameSession(null, null, 0, 0, null, null);
		@Test
		public void testNewGameName(){
			final MainView testTabName = new MainView();
			final String tabType = "New Game";
			final String result = testTabName.tabLabler(tabType, game);
			assertEquals("New Game", result);
			
		}
		@Test
		public void testPlayGameName(){
			final MainView testTabName = new MainView();
			final String tabType = "Play Game";
			final String result = testTabName.tabLabler(tabType, game);
			assertEquals(game.getGameName(), result);
		}
		@Test
		public void testEditGameName(){
			final MainView testTabName = new MainView();
			final String tabType = "Edit Game";
			final String result = testTabName.tabLabler(tabType, game);
			assertEquals(game.getGameName(), result);
		}
		@Test
		public void testReqTabName(){
			final MainView testTabName = new MainView();
			final String tabType ="Req Tab";
			final String result = testTabName.tabLabler(tabType, game);
			assertEquals("New Requirement", result);
		}

	}