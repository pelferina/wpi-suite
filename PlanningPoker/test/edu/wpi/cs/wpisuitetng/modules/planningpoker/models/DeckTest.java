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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;

public class DeckTest {
	MockData db;
	String mockSsid;
	DeckEntityManager d_manager;
	AddDeckController dController;
	Session adminSession;
	Project testProject;
	List<Integer> testCards1; 
	Deck d1;
	
	List<Integer> testCards2;
	Deck d2;
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");	
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		db = new MockData(new HashSet<Object>());
		db.save(admin);
		d_manager = new DeckEntityManager(db);
		dController = AddDeckController.getInstance();
		
		testCards1 = Arrays.asList(0,1,2,3,4,5);
		d1 = new Deck("Test Deck1", testCards1);
		testCards2 = Arrays.asList(6,7,8,9,10);
		d2 = new Deck("Test Deck2", testCards2);
	}	
	
	// Tests whether a Deck can be created successfully.

	@Test
	public void testCreateDeck(){
		assertNotNull(d1);
		assertEquals("Test Deck1", d1.getName());
		//assertEquals(1, d1.getId());
		assertEquals(testCards1, d1.getCards());
		System.out.println(DeckModel.getInstance().getDeck(0).toString());
		System.out.println(DeckModel.getInstance().getDeck(1));
	}
	
	@Test
	public void testAddDeck(){
		DeckModel.getInstance().addDeck(d1);
		assertEquals(d1, DeckModel.getInstance().getDeck(1));
		assertEquals(2, DeckModel.getInstance().getSize());
	}
	
	@Test
	public void testAddDecks(){
		Deck[] decks = {d1, d2};
		DeckModel.getInstance().addDecks(decks);
		//assertEquals(d1, DeckModel.getInstance().getDeck(2));
		//assertEquals(d2, DeckModel.getInstance().getDeck(3));
		assertEquals(4, DeckModel.getInstance().getSize());
	}

}	

