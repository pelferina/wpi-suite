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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.RefreshManager;

public class testDifferentList {


	@Before
	public void setUp() throws Exception {
//		Network.initNetwork(new MockNetwork());
//		Network.getInstance().setDefaultNetworkConfiguration(
//				new NetworkConfiguration("http://wpisuitetng"));

	}	
	
	// Tests whether a gamesession can be created successfully.
	@Test
	public void testGameSessionCreate(){
		List<GameSession> gs = new ArrayList<GameSession>();
		gs.add(new GameSession("Name", "", "", 0, Calendar.getInstance().getTime(), new ArrayList<Integer>()));
		
		List<GameSession> gs2 = new ArrayList<GameSession>();
		gs2.add(new GameSession("Name1", "", "", 0, Calendar.getInstance().getTime(), new ArrayList<Integer>()));
		
		RefreshManager r = new RefreshManager();
		assertTrue(r.differentList(gs, gs2));
		assertTrue(r.differentList(new ArrayList<GameSession>(), gs2));
		assertTrue(r.differentList(gs, new ArrayList<GameSession>()));
		assertFalse(r.differentList(gs, gs));



		
	}
	
	
}
