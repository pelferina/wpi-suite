package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckManagingPanel;

public class ManageDeckTest {
	
	@Test
	public void testManageDeck(){
		DeckManagingPanel dmp = new DeckManagingPanel();
		assertNotNull(dmp);
	}

}
