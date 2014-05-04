/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Cosmic Latte
 ******************************************************************************/

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
			final NewRequirementPanel testreqPanel = new NewRequirementPanel(null);
			testreqPanel.panelSetup();
			final Requirement testRequirement = new Requirement();
			final String testName = "test: Name";
			final String testDescription = "test: Description";
			final JTextField testnameField = new JTextField();  
			final JButton reqbutton = testreqPanel.getCreateRequirementButton();
			final JLabel reqError = testreqPanel.getReqError();
			assertEquals(false, reqbutton.isEnabled());
			assertEquals(true, reqError.isEnabled());
			
		}
		
	}