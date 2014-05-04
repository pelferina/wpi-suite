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

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;


public class VoteTester {
	
	@Test
	public final void testVoteClassMethods()
	{
		final Vote v = new Vote(null, 0, 1);
		final ArrayList<Integer> votes = new ArrayList<Integer>();
		votes.add(1);votes.add(2);votes.add(3);votes.add(4);votes.add(5);
		v.setVote(votes);
		assertFalse(v.getVote().get(0) != 1);
		assertTrue(v.getUID() == 0);
		assertTrue(v.getGameID() == 1);
		v.setUID(100);
		v.setGameID(10);
		assertTrue(v.getGameID() == 10);
		assertTrue(v.getUID() == 100);
		final Vote x = v.fromJson(v.toJSON());
		assertTrue(x.equals(v));
		assertTrue(x.identify(v));
		assertTrue(x.compareTo(v) == 0);
		v.setVoteID(0);
		assertTrue(v.getVoteID() == 0);
		v.save();
		v.delete();
		final Vote v2 = new Vote(null, 1);
		assertFalse(v.equals(v2));
		assertFalse(v.identify(null));
		assertFalse(v.identify(v2));
		assertTrue(v2.getUID() == -1);
		v2.setUID(0);
		v2.setGameID(0);
		assertTrue(Vote.toHash(v2) == 0);
		assertFalse(v.equals(""));
		
	}
	@Test
	public final void testNullManager() throws WPISuiteException{
		final VoteEntityManager nullManager = new VoteEntityManager(null);
		try{
			nullManager.makeEntity(null, (new Vote(null, 0, 0)).toJSON());
		}catch(Exception E) {
			assertTrue(true);
		}
		try{
			nullManager.getAll(null);
		}catch(Exception E) {
			assertTrue(true);
		}
		try{
			nullManager.update(null, (new Vote(null, 0, 0)).toJSON());
		}catch(Exception E) {
			assertTrue(true);
		}
		assertNull(nullManager.advancedGet(null, null));
		assertNull(nullManager.advancedPost(null, null, null));
		assertTrue(nullManager.Count() == 0);
		nullManager.deleteAll(null);
		nullManager.deleteEntity(null, null);
		try{
			nullManager.save(null, null);
		}catch(Exception E) {
			assertTrue(true);
		}
		
	}
}
