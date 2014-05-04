package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.IterationModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class InputPanelTest {
	private Requirement req1;
	private Requirement req2;
	private Requirement req3;
	
	private List<Requirement> reqs;
	private final JButton btnClose = new JButton("x");

	/**
	 * Setting up using Network and Iteration
	
	 * @throws Exception */
	@Before
	public void setUp() throws Exception {
		// Mock Network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		reqs = null;
		req1 = new Requirement(1,"one","Desc");
		req2 = new Requirement(2,"two","Desc");
		req3 = new Requirement(3,"three","Desc");
		RequirementModel.getInstance().addRequirement(req1);
		RequirementModel.getInstance().addRequirement(req2);
		RequirementModel.getInstance().addRequirement(req3);
	}

//	// Mock Iteration
//	GameSession gameSession = new GameSession("","",0,0, new Date(), new List());
//	IterationModel.getInstance().setBacklog(iterationTest);
	@Test
	public void testDeadLineFocus(){
		
		NewGameInputDistributedPanel testInputPanel = new NewGameInputDistributedPanel(null);
		boolean testDeadline = testInputPanel.hasDeadline();
		boolean testFocus = testInputPanel.hasFocus();
		assertEquals(false, testDeadline);
		assertEquals(false, testFocus);
	}
	
	/**
	 * check whether the field is enabled/visible or not as default
	 */
	@Test 
	public void defaultEnability()
	{
		// Create new game panel
		NewGameDistributedPanel ngdp = new NewGameDistributedPanel(reqs, null);
		NewGameInputDistributedPanel testNew = new NewGameInputDistributedPanel(ngdp);
		
		// Check
		assertEquals(true, testNew.getNameLabel().isEnabled());
		assertEquals(true, testNew.getNameTextField().isEnabled());
		
		assertEquals(true, testNew.getDescriptionLabel().isEnabled());
		assertEquals(true, testNew.getDescriptionScrollPane().isEnabled());

		assertEquals(true, testNew.getDeadlineLabel().isEnabled());
		assertEquals(true, testNew.getDeadlineCheckBox().isEnabled());
		assertEquals(false, testNew.getDatePicker().isVisible());
		assertEquals(false, testNew.getDeadlineMinuteComboBox().isVisible());
		assertEquals(false, testNew.getDeadlineHourComboBox().isVisible());
		assertEquals(false, testNew.getAMButton().isVisible());
		assertEquals(false, testNew.getPMButton().isVisible());
		
		assertEquals(true, testNew.getDeckLabel().isEnabled());
		assertEquals(true, testNew.getDeckCheckBox().isEnabled());
		
		assertEquals(false, testNew.getCreateDeckButton().isVisible());
		assertEquals(true, testNew.getCancelButton().isEnabled());
		assertEquals(false, testNew.getActivateGameButton().isEnabled());
		assertEquals(false, testNew.getSaveGameButton().isEnabled());		
	}
	
	/**
	 * check for enability when required fields are not filled in
	 */
	@Test
	public void errorRequiredFieldTest() {
		// Create new game panel
		NewGameDistributedPanel ngdp = new NewGameDistributedPanel(reqs, null);
		NewGameInputDistributedPanel testNew = new NewGameInputDistributedPanel(ngdp);
				
		
		// a field is added correctly but both name and description are filled with blanks
		ngdp.getNewGameReqPanel().getAddOneButton().doClick();
		testNew.getNameTextField().setText("");
//		
		// can't create because no name/description, but a field has been changed
		assertFalse(testNew.getSaveGameButton().isEnabled());
		assertFalse(testNew.getActivateGameButton().isEnabled());

		

	}
}
