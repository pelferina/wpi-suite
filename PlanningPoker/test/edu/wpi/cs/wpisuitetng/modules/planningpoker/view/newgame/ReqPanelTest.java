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

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class ReqPanelTest {
	
	@Test
	public void testAddRemoveButtons(){
		final Requirement req1 = new Requirement();
		final Requirement req2 = new Requirement();
		final Requirement req3 = new Requirement();
		final List<Requirement> reqs = new ArrayList<Requirement>();
		reqs.add(req1);
		reqs.add(req2);
		reqs.add(req3);
		final NewGameReqPanel reqPanel = new NewGameReqPanel(null);
		final JTable unselected = reqPanel.getReqsTable();
		final JTable selected = reqPanel.getSelectedTabel();
		final JButton addOne = reqPanel.getAddOneButton();
		final JButton addAll = reqPanel.getAddAllButton();
		final JButton removeOne = reqPanel.getRemoveOneButton();
		final JButton removeAll = reqPanel.getRemoveAllButton();
		unselected.setRowSelectionInterval(0, 1);
		addOne.doClick();
		List<Requirement> selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.contains(req1));
		assertFalse(selectedReqs.contains(req2));
		addOne.doClick();
		selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.contains(req2));
		selected.setRowSelectionInterval(0, 1);
		removeOne.doClick();
		selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.contains(req2));
		assertFalse(selectedReqs.contains(req1));
		removeOne.doClick();
		selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.size() == 0);
		addAll.doClick();
		selectedReqs = reqPanel.getSelected();
		assertTrue(selectedReqs.size() == 3);
		removeAll.doClick();
		selectedReqs = reqPanel.getSelected();
		assertFalse(selectedReqs.contains(req1));
		assertFalse(selectedReqs.contains(req2));
		assertFalse(selectedReqs.contains(req3));
	}
	
}
