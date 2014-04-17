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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class PlanningPokerEntityManagerTest {
	MockData db;
	Requirement req1, req2;
	String mockSsid;
	GameEntityManager p_manager;
	EmailAddressEntityManager e_manager;
	Session adminSession;
	Session bobSession;
	Project testProject;
	AddEmailAddressController eController;
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		User bob = new User("bob", "bob", "1234", 28);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		bobSession = new Session(bob, testProject, "bob123");
		
		req1 = new Requirement(1, "Bob", "1.0", RequirementStatus.NEW, RequirementPriority.BLANK, "Desc", 1, 1);
		req2 = new Requirement(2, "Tom", "2.0", RequirementStatus.NEW, RequirementPriority.BLANK, "Desc", 1, 1);

		
		db = new MockData(new HashSet<Object>());
		db.save(req1, testProject);
		db.save(req2, testProject);

		db.save(admin);
		p_manager = new GameEntityManager(db);
		e_manager = new EmailAddressEntityManager(db);
		eController = new AddEmailAddressController();

	}	
	//@Test
	public void testDeadlineCheck(){
		Date deadline = new Date();
		deadline.setSeconds(deadline.getSeconds()+5);
		
		GameSession testGame = new GameSession("test game", null, 1, 1, deadline , null);
		db.save(testGame, testProject);
		assertTrue(testGame.getGameStatus().compareTo(GameStatus.DRAFT)==0);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(testGame.getGameStatus().compareTo(GameStatus.ARCHIVED)==0);	
	}
	@Test
	public void testStats(){
		ArrayList<Integer> reqList = new ArrayList<Integer>();
		ArrayList<Vote> voteList = new ArrayList<Vote>();
		ArrayList<Integer> vote11 = new ArrayList<Integer>();
		ArrayList<Integer> vote22 = new ArrayList<Integer>();
		vote11.add(12);
		vote11.add(32);
		vote22.add(21);
		vote22.add(17);
		Vote vote1 = new Vote(vote11,1,1);
		Vote vote2 = new Vote(vote22,2,1);
		
		
		reqList.add(1);
		reqList.add(2);
		GameSession testGame = new GameSession("test game", null, 1, 1, null , reqList);
		
		testGame.addVote(vote1);
		testGame.addVote(vote2);
		
		testGame.calculateStats();
		System.out.println(testGame.getMean());
		System.out.println(testGame.getMedian());
		
	}
}
