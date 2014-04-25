package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputPanelTest {

	@Test
	public void testDeadLineFocus(){
		
		NewGameInputDistributedPanel testInputPanel = new NewGameInputDistributedPanel(null);
		boolean testDeadline = testInputPanel.hasDeadline();
		boolean testFocus = testInputPanel.hasFocus();
		assertEquals(false, testDeadline);
		assertEquals(false, testFocus);
	}
	
}
