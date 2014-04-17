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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class EmailAddressTest {

	MockData db;
	Requirement req1;
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

		
		db = new MockData(new HashSet<Object>());
		db.save(req1, testProject);

		db.save(admin);
		p_manager = new GameEntityManager(db);
		e_manager = new EmailAddressEntityManager(db);
		eController = new AddEmailAddressController();
	}	
	
	@Test
	public void testSendEmail() throws UnsupportedEncodingException{
		GameSession gs = new GameSession("Test Game", "Test Description", 0, 1, new Date(1, 1, 1), new ArrayList<Integer>());
		//Fixed accordin to http://stackoverflow.com/questions/10944448/instanceof-vs-isinstance
		
		
//		try {
//			try {
//				e_manager.makeEntity(adminSession, "email1@localhost");
//				e_manager.makeEntity(bobSession, "email2@localhost");
//				
//			} catch (BadRequestException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (ConflictException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (WPISuiteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		try {
			p_manager.endGame(gs.getGameID(), adminSession.getProject());
		} catch (WPISuiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
