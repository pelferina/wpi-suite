/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class GameSessionTest {

	@Before
	public void setUp() throws Exception {
//		Network.initNetwork(new MockNetwork());
//		Network.getInstance().setDefaultNetworkConfiguration(
//				new NetworkConfiguration("http://wpisuitetng"));

	}
	
	// Tests whether a gamesession can be created successfully.
	@Test
	public void testGameSessionCreate(){
		final GameSession gs = new GameSession("Test Game", "Test Description", 0, 1, Calendar.getInstance().getTime(), new ArrayList<Integer>());
		assertNotNull(gs);
		assertEquals("Test Game", gs.getGameName());
		assertEquals(0, gs.getOwnerID());
		assertEquals(1, gs.getGameID());
		assertNotNull(gs.toString());
		
	}
	
	@Test
	public void testSetDeadline(){
		final GameSession gs = new GameSession("Test Game", "Test Description", 0, 1, Calendar.getInstance().getTime(), new ArrayList<Integer>());
		final Date deadline = Calendar.getInstance().getTime();
		
		gs.setEndDate(deadline);
		assertTrue(gs.getEndDate().equals(deadline));
	}
	
	
	
	@Test
	public void testDefaultGameStatus(){
		final GameSession gs = new GameSession("Test Game", "Test Description", 0, 1, new Date(), new ArrayList<Integer>());
		assertEquals(GameStatus.DRAFT, gs.getGameStatus());
	}
	
	@Test
	public void testGetDraftGameSessionList(){
		final GameModel gm = GameModel.getInstance();
		final GameSession gs1 = new GameSession("Test Game1", "Test Description", 0, 1, new Date(), new ArrayList<Integer>());
		final GameSession gs2 = new GameSession("Test Game2", "Test Description", 0, 2, new Date(), new ArrayList<Integer>());
		final GameSession gs3 = new GameSession("Test Game3", "Test Description", 0, 3, new Date(), new ArrayList<Integer>());
		gs2.setGameStatus(GameStatus.ACTIVE);
		gs3.setGameStatus(GameStatus.COMPLETED);
		gm.addGame(gs1);
		gm.addGame(gs2);
		gm.addGame(gs3);
		final List<GameSession> draftGameSessionList = new ArrayList<GameSession>();
		draftGameSessionList.add(gs1);
		int i = 0;
		for (GameSession expectedGameSession: draftGameSessionList){
			//assertTrue(expectedGameSession.equals(gm.getDraftGameSessions().get(i)));
			i++;
		}
		//System.out.println(gm.getDraftGameSessions().toString());
		//System.out.println(gm.getActiveGameSessions().toString());
	}
	
	@Test
	public void testGetActiveGameSessionList(){
		final GameModel gm = GameModel.getInstance();
		final GameSession gs1 = new GameSession("Test Game1", "Test Description", 0, 1, new Date(), new ArrayList<Integer>());
		final GameSession gs2 = new GameSession("Test Game2", "Test Description", 0, 2, new Date(), new ArrayList<Integer>());
		final GameSession gs3 = new GameSession("Test Game3", "Test Description", 0, 3, new Date(), new ArrayList<Integer>());
		gs2.setGameStatus(GameStatus.ACTIVE);
		gs3.setGameStatus(GameStatus.COMPLETED);
		gm.addGame(gs1);
		gm.addGame(gs2);
		gm.addGame(gs3);
		final List<GameSession> activeGameSessionList = new ArrayList<GameSession>();
		activeGameSessionList.add(gs2);
		int i = 0;
		for (GameSession expectedGameSession: activeGameSessionList){
			//assertTrue(expectedGameSession.equals(gm.getActiveGameSessions().get(i)));
			i++;
		}
		//System.out.println(gm.getActiveGameSessions().toString());
	}
	
	@Test
	public void testGetPastGameSessionList(){
		final GameModel gm = GameModel.getInstance();
		final GameSession gs1 = new GameSession("Test Game1", "Test Description", 0, 1, new Date(), new ArrayList<Integer>());
		final GameSession gs2 = new GameSession("Test Game2", "Test Description", 0, 2, new Date(), new ArrayList<Integer>());
		final GameSession gs3 = new GameSession("Test Game3", "Test Description", 0, 3, new Date(), new ArrayList<Integer>());
		gs2.setGameStatus(GameStatus.ACTIVE);
		gs3.setGameStatus(GameStatus.COMPLETED);
		gm.addGame(gs1);
		gm.addGame(gs2);
		gm.addGame(gs3);
		final List<GameSession> pastGameSessionList = new ArrayList<GameSession>();
		pastGameSessionList.add(gs3);
		int i = 0;
		for (GameSession expectedGameSession: pastGameSessionList){
			assertTrue(expectedGameSession.equals(gm.getCompletedGameSessions().get(i)));
			i++;
		}
		//System.out.println(gm.getCompletedGameSessions().toString());
	}	
}	


