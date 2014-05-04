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

import org.junit.Test;

	public class reqtest {
		@Test
		public void testUpdaterequirement(){
			final NewRequirementPanel testreqPanel = new NewRequirementPanel(null);
			testreqPanel.panelSetup();
			final JButton reqbutton = testreqPanel.getCreateRequirementButton();
			final JLabel reqError = testreqPanel.getReqError();
			assertEquals(false, reqbutton.isEnabled());
			assertEquals(true, reqError.isEnabled());
			
		}
		
	}