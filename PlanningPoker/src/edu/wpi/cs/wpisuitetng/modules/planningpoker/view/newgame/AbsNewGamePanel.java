/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public abstract class AbsNewGamePanel extends JSplitPane{
	NewGameReqPanel newGameReqPanel;
	AbsNewGameInputPanel newGameInputPanel;

	
	public void updatePanels(Requirement requirement){
		newGameReqPanel.addReq(requirement);
	}
	
	public Requirement getSelectedRequirement(){
		return newGameReqPanel.removeSelected();
	}

}
