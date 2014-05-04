/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.IterationModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class ReqPanelTest {
	private Requirement req1;
	private Requirement req2;
	private Requirement req3;
	/**
	 * Setting up using Network and Iteration
	
	 * @throws Exception */
	
	@Before
	public void setUp() {
		req1 = new Requirement(1,"one","Desc");
		req2 = new Requirement(2,"two","Desc");
		req3 = new Requirement(3,"three","Desc");
		RequirementModel.getInstance().addRequirement(req1);
		RequirementModel.getInstance().addRequirement(req2);
		RequirementModel.getInstance().addRequirement(req3);
	}
	
	@Test
	public void testAddRemoveButtons(){
		NewGameReqPanel reqPanel = new NewGameReqPanel(null);
		JTable unselected = reqPanel.getReqsTable();
		JTable selected = reqPanel.getSelectedTabel();
		JButton addOne = reqPanel.getAddOneButton();
		JButton addAll = reqPanel.getAddAllButton();
		JButton removeOne = reqPanel.getRemoveOneButton();
		JButton removeAll = reqPanel.getRemoveAllButton();
		addOne.doClick();
		List<Requirement> selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.contains(req1));
		assertFalse(selectedReqs.contains(req2));
		assertFalse(selectedReqs.contains(req3));
		addOne.doClick();
		selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.contains(req1));
		assertTrue(selectedReqs.contains(req2));
		assertFalse(selectedReqs.contains(req3));
		removeOne.doClick();
		selectedReqs = reqPanel.getSelected();
		assertFalse(selectedReqs.contains(req2));
		assertTrue(selectedReqs.contains(req1));
		assertFalse(selectedReqs.contains(req3));
		removeOne.doClick();
		selectedReqs = reqPanel.getSelected();
		//assertEquals(0, selectedReqs.size());
		addAll.doClick();
		selectedReqs = reqPanel.getSelected();
		//assertEquals(3, selectedReqs.size());
		System.out.println(selectedReqs.toString());
		removeAll.doClick();
		selectedReqs = reqPanel.getSelected();
		assertFalse(selectedReqs.contains(req1));
		assertFalse(selectedReqs.contains(req2));
		assertFalse(selectedReqs.contains(req3));
	}
	
}
