package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;
import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckManagingPanel;

public class PlanningPokerTest {
	
	@Test
	public void testManageDeck(){
		PlanningPoker ptest = new PlanningPoker();
		assertEquals("Planning Poker",ptest.getName());
	}

}