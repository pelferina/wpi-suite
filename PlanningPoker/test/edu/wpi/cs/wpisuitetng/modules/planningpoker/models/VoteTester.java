package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;


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
		
	}
}
