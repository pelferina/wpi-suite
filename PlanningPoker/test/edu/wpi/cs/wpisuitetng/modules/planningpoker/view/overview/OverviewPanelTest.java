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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Anthony
 *
 */
public class OverviewPanelTest {

	@Test
	public void test() {
		OverviewPanel op = new OverviewPanel();
		assertNotNull(op);
		op.updateTable("Drafts");
		op.updateTable("All Games");
		op.updateTable("My Games");
		op.updateTable("In Progress");
		op.updateTable("Active");
		op.updateTable("Needs Vote");
		op.updateTable("Voted");
		op.updateTable("History");
		op.updateTable("Complete");
		op.updateTable("Completed");
		op.updateTable("Archived");
		op.refreshGames();
	}

}
