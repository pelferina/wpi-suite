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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.CompleteView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.GameData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;


public class LoadGameTest {
	private Requirement req1;
	private Requirement req2;
	private Requirement req3;
	User admin;
	MockData db;
	String mockSsid;
	GameEntityManager p_manager;
	Session adminSession;
	Project testProject;
	/**
	 * Setting up using Network and Iteration
	
	 * @throws Exception */
	
	@Before
	public void setUp() throws Exception {
		admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		req1 = new Requirement(1,"one","Desc");
		req2 = new Requirement(2,"two","Desc");
		req3 = new Requirement(3,"three","Desc");
		RequirementModel.getInstance().addRequirement(req1);
		RequirementModel.getInstance().addRequirement(req2);
		RequirementModel.getInstance().addRequirement(req3);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		db = new MockData(new HashSet<Object>());
		db.save(admin);
		p_manager = new GameEntityManager(db);
		GetCurrentUser gcu =  GetCurrentUser.getInstance();
		assertNotNull(gcu);
		gcu.enableTesting();
	}	

	@Test
	public void testLoadCompleteGame(){
		ArrayList<Integer> reqArray = new ArrayList<Integer>();
		reqArray.add(1);
		GameSession testGame = new GameSession("Test Game", "Test Description", 0, 0, new Date(), reqArray);
		testGame.setGameStatus(GameStatus.COMPLETED);
		Vote v = new Vote(reqArray, testGame.getGameID());
		List<Vote> votes = new ArrayList<Vote>();
		votes.add(v);
		testGame.setVotes(votes);
		CompleteView cv = new CompleteView(testGame);
		cv.getVoteData();
		GameData gd = cv.getGameData();
		assertTrue(gd.getGameNameTextBox().getText().equals("Test Game"));
		assertTrue(gd.getDescriptionTextArea().getText().equals("Test Description"));
	}
}
