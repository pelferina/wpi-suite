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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputPanelTest {

	@Test
	public void testDeadLineFocus(){
		
		final NewGameInputDistributedPanel testInputPanel = new NewGameInputDistributedPanel(null);
		final boolean testDeadline = testInputPanel.hasDeadline();
		final boolean testFocus = testInputPanel.hasFocus();
		assertEquals(false, testDeadline);
		assertEquals(false, testFocus);
	}
	
}
