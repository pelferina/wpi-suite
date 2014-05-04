package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;

public class PlanningPokerTest {
	
	@Test
	public void testManageDeck(){
		PlanningPoker ptest = new PlanningPoker();
		assertEquals("Planning Poker",ptest.getName());
	}

}