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
		Vote v = new Vote(null, 0, 1);
		ArrayList<Integer> votes = new ArrayList<Integer>();
		votes.add(1);votes.add(2);votes.add(3);votes.add(4);votes.add(5);
		v.setVote(votes);
		assertFalse(1 != v.getVote().get(0));
		assertTrue(0 == v.getUID());
		assertTrue(1 == v.getGameID());
		v.setUID(100);
		v.setGameID(10);
		assertTrue(10 == v.getGameID());
		assertTrue(100 == v.getUID());
		Vote x = v.fromJson(v.toJSON());
		assertTrue(x.equals(v));
		assertTrue(x.identify(v));
		assertTrue(x.compareTo(v) == 0);
		v.setVoteID(0);
		assertTrue(v.getVoteID() == 0);
		v.save();
		v.delete();
		Vote v2 = new Vote(null, 1);
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
		VoteEntityManager nullManager = new VoteEntityManager(null);
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
		assertTrue(0 == nullManager.Count());
		nullManager.deleteAll(null);
		nullManager.deleteEntity(null, null);
		try{
			nullManager.save(null, null);
		}catch(Exception E) {
			assertTrue(true);
		}
		
	}
}
