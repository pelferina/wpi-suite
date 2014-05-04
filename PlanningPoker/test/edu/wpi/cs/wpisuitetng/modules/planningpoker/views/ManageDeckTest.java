package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckManagingPanel;

public class ManageDeckTest {
	final JButton btnClose = new JButton("x");
	@Test
	public void testManageDeck(){
		DeckManagingPanel dmp = new DeckManagingPanel(btnClose);
		assertNotNull(dmp);
	}

}
