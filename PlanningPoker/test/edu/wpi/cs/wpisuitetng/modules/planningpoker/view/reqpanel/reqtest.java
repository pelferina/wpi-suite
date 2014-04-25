package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.reqpanel;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameReqPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanel;

	public class reqtest {
		private NewGameDistributedPanel newGamePanel;
		@Test
		public void testUpdaterequirement(){
			NewRequirementPanel testreqPanel = new NewRequirementPanel(null);	
			testreqPanel.panelSetup();
			Requirement testRequirement = new Requirement();
			String testName = "test: Name";
			String testDescription = "test: Description";
			JTextField testnameField = new JTextField(); ; 
			JButton reqbutton = testreqPanel.getCreateRequirementButton();
			JLabel reqError = testreqPanel.getReqError();
			assertEquals(false, reqbutton.isEnabled());
			assertEquals(true, reqError.isEnabled());
			
		}
		
	}