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
import java.util.List;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;


public class TestVoteData {
	
	
	@Test
	public void testVote(){
		final int req1 = 0;
		final List<Integer> reqs = new ArrayList<Integer>();
		reqs.add(req1);
		new GameSession("Test Game", "Test Description", 0, 0, new Date(), reqs);
	}
	
}