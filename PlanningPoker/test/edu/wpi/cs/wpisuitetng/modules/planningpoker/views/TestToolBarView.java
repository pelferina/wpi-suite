package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;
import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.PlanningPokerButtonsPanel;

public class TestToolBarView {

	@Test
	public void gameButton(){
		ToolbarView testGameButton= new ToolbarView(true);
		PlanningPokerButtonsPanel result = testGameButton.getReqButton();
		assertTrue(result.isEnabled());
	}
	
}